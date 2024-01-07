package com.example.controller;

import com.example.pojo.Apply;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.ApplyService;
import com.example.service.CourseService;
import com.example.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;
    @Autowired
    private CourseService courseService;

    /**
     * 提交缓考申请表
     * @param apply 申请表信息
     */
    @PostMapping
    public Result addApply(@RequestBody Apply apply) {
        log.info("学生填写缓考申请表：{}", apply);
        Map<String, Object> map = ThreadLocalUtil.get();
        apply.setUniversity((String) map.get("university"));

        //是否存在该学生对该课程的缓考申请表
        Apply a = applyService.findApplyById(apply.getStuId(), apply.getCourseId(), apply.getUniversity(),
                apply.getYear());
        if (a == null) {
            applyService.addApply(apply);
        } else {
            return Result.error("请勿重复提交对同样课程的缓考申请");
        }
        return Result.success();
    }

    /**
     * 分页查询缓考申请表
     * @param page       页码
     * @param pageSize   每页数据量
     * @param begin      申请表创建时间范围（开始时间）
     * @param end        申请表创建时间范围（结束时间）
     * @param year       学年
     * @param trimesters 学期
     * @return 页面封装数据
     */
    @GetMapping
    public Result<PageBean> findApplyPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime begin,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime end,
                                          String year, Integer trimesters, String courseName, Integer stuId, Integer staffId) {

        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");

        log.info("分页查询缓考申请表参数：{}，{}，{}，{}，{}，{}，{}，{}，{}, {}",
                page, pageSize, begin, end, year, trimesters, courseName, university, stuId, staffId);

        //根据courseName模糊匹配课程的id列表
        List<Integer> courseIds = null;
        if (courseName != null) {
            courseIds = courseService.findTeacherByNameIds(courseName, university);
        }
        if ((stuId != null && staffId != null) || (stuId == null && staffId == null)){
            return Result.error("用户身份不明确");
        }
        PageBean pageBean = applyService.page(page, pageSize, begin, end, year, trimesters, courseIds, university, stuId, staffId);
        return Result.success(pageBean);
    }

    /**
     * 更新缓考申请表
     * @param apply 申请表信息
     */
    @PutMapping
    public Result updateApply(@RequestBody Apply apply) {

        //判断必要参数是否为空
        if (apply.getReason() == null || apply.getAdministrator() == null ||
                apply.getCourseId() == null || apply.getYear() == null) {
            return Result.error("必要参数有空数据");
        }

        log.info("更新缓考申请表：{}，{}", apply.getReason(), apply.getAdministrator());

        //从线程获取学号和学校
        Map<String, Object> map = ThreadLocalUtil.get();
        apply.setUniversity((String) map.get("university"));
        apply.setStuId((Integer) map.get("id"));

        //判断申请表是否处于提交状态
        Apply a = applyService.findApplyById(apply.getStuId(), apply.getCourseId(), apply.getUniversity(),
                apply.getYear());

        //各种情况下的返回结果
        if (a.getSubmit() == 0 && a.getSituation() == 2) {
            applyService.updateApply(apply);
        } else if (a.getSubmit() == 1 && a.getSituation() == 1) {
            return Result.error("审核已通过，无需编辑");
        } else if (a.getSubmit() == 1 && a.getSituation() == 0) {
            return Result.error("审核未通过，请重新填写一份提交");
        } else {
            return Result.error("请先撤销提交再执行更新操作");
        }

        return Result.success();
    }

    /**
     * 删除缓考申请表
     * @param requestPayload 请求数据
     */
    @DeleteMapping
    public Result deleteApply(@RequestBody  Map<String, Object> requestPayload) {

        // 从requestPayload中获取 courseId 和 year
        Integer courseId = (Integer) requestPayload.get("courseId");
        String year = (String) requestPayload.get("year");

        //判断数据是否为空
        if (courseId == null || year == null) {
            return Result.error("必须的参数有空的数据");
        }

        //从线程获取学号和学校
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer stuId = (Integer) map.get("id");
        log.info("删除缓考申请表：{}，{}，{}，{}", courseId, year, university, stuId);

        //申请表是否已审核
        Apply applyById = applyService.findApplyById(stuId, courseId, university, year);
        if (applyById.getSituation() != 2){
            return Result.error("申请表已审核，无法删除");
        }

        applyService.deleteApply(courseId, year, stuId, university);
        return Result.success();
    }

    /**
     * 撤销缓考申请表
     * @param requestPayload
     * -courseId 课程编号
     * -year 学年
     */
    @PatchMapping("/revoke")
    public Result changeSubmit(@RequestBody Map<String, Object> requestPayload){

        // 从requestPayload中获取 courseId 和 year
        String year = (String) requestPayload.get("year");
        Integer courseId = (Integer) requestPayload.get("courseId");

        //判断数据是否为空
        if (courseId == null || year == null) {
            return Result.error("必须的参数有空的数据");
        }

        //从线程获取学号和学校
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer stuId = (Integer) map.get("id");
        log.info("撤销缓考申请表：{}，{}，{}，{}", courseId, year, university, stuId);

        //申请表是否已审核
        Apply applyById = applyService.findApplyById(stuId, courseId, university, year);
        if (applyById.getSituation() != 2){
            return Result.error("申请表已审核，无法撤销");
        }

        applyService.changeSubmit(courseId, year, stuId, university);
        return Result.success();
    }

    /**
     * 审核缓考申请表
     * @param requestPayload
     * -courseId 课程编号
     * -year 学年
     * -situation 审核结果信号
     */
    @PatchMapping("/audit")
    public Result audit(@RequestBody Map<String, Object> requestPayload){

        //从线程获取学号和学校
        Map<String, Object> map = ThreadLocalUtil.get();
        String university = (String) map.get("university");
        Integer stuId = (Integer) map.get("id");

        // 从requestPayload中获取 courseId、year、situation
        Integer courseId = (Integer) requestPayload.get("courseId");
        String year = (String) requestPayload.get("year");
        Integer situation = (Integer) requestPayload.get("situation");

        //判断数据是否为空
        if (courseId == null || year == null || situation == null) {
            return Result.error("必须的参数有空的数据");
        } else if (situation >= 2 || situation < 0){
            return Result.error("审核参数不满足规定");
        }

        log.info("审核缓考申请表：{}，{}，{}，{}，{}", courseId, year, situation, university, stuId);

        applyService.audit(courseId, year, situation, university, stuId);
        return Result.success();
    }

}
