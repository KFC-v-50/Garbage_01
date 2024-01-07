<template>
  <div class="course-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-book"></i> 课程管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :span="2">
            <el-button @click="create" icon="el-icon-plus">创建</el-button>
          </el-col>
          <el-col :offset="10" :span="2">
            <el-input @keyup.enter.native="query" placeholder="课程名" v-model="queryForm.courseName" />
          </el-col>
          <el-col :span="2">
            <el-input @keyup.enter.native="query" placeholder="教师名" v-model="queryForm.teacherName" />
          </el-col>
          <el-col :span="2">
            <el-input @keyup.enter.native="query" placeholder="学院名" v-model="queryForm.college" />
          </el-col>
          <el-col :span="3">
            <el-input @keyup.enter.native="query" placeholder="课程性质" v-model="queryForm.type" />
          </el-col>
          <el-col :span="3">
            <el-button @click="query" icon="el-icon-search" type="primary">搜索
            </el-button>
          </el-col>
        </el-row>
      </div>

      <el-row justify="center" type="flex">
        <el-pagination :current-page.sync="pageIndex" :page-size="pageSize" :total="total" @current-change="getPage"
          background layout="prev, pager, next">
        </el-pagination>
      </el-row>

      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column label="课程Id" prop="courseId" />
          <el-table-column label="课程名" prop="courseName" />
          <el-table-column label="教师" prop="teacherName" />
          <el-table-column label="所属学院" prop="college" />
          <el-table-column label="课程性质" prop="type" />
          <el-table-column label="学分" prop="credit" />
          <el-table-column align="center" label="上课时间" prop="schedule" width="150px" />
          <el-table-column label="已选人数" prop="selected" />
          <el-table-column label="最大容量" prop="volume" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button @click="edit(scope.row.courseId)" size="mini" type="success">编辑
              </el-button>
              <el-button @click="deleteItem(scope.row.courseId)" size="mini" type="danger">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-dialog :visible.sync="editing" title="编辑" width="30%">
        <el-form :model="entityForm" label-width="70px" ref="form">
          <el-form-item label="课程id">
            <el-input type="number" v-model="entityForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="课程名">
            <el-input v-model="entityForm.courseName"></el-input>
          </el-form-item>
          <!-- <el-form-item label="授课教师">
            <el-select placeholder="请选择教师" v-model="entityForm.teacherId">
              <el-option :key="index" :label="item.name" :value="item.id" v-for="(item, index) in teachers" />
            </el-select>
          </el-form-item> -->
          <el-form-item label="授课教师">
            <el-input type="number" v-model="entityForm.teacherId"></el-input>
          </el-form-item>
          <!-- <el-form-item label="年级">
            <el-input type="number" v-model="entityForm.grade"></el-input>
          </el-form-item> -->

          <el-form-item label="上课时间">
            <el-select v-model="entityForm.date">
              <el-option :key="index" :label="item.name" :value="item.id" v-for="(item, index) in days">
              </el-option>
            </el-select>
            <el-select v-model="entityForm.time">
              <el-option :key="index" :label="item.name" :value="item.id" v-for="(item, index) in times">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="时长(节)">
            <el-input type="number" v-model="courseLength"></el-input>
          </el-form-item> -->
          <el-form-item label="上课地点">
            <el-input v-model="entityForm.classroom"></el-input>
          </el-form-item>
          <el-form-item label="课程性质">
            <el-input v-model="entityForm.type"></el-input>
          </el-form-item>
          <el-form-item label="开课学院">
            <el-input v-model="entityForm.college"></el-input>
          </el-form-item>
          <el-form-item label="学分">
            <el-input type="number" v-model="entityForm.credit"></el-input>
          </el-form-item>
          <el-form-item label="最大容量">
            <el-input type="number" v-model="entityForm.volume"></el-input>
          </el-form-item>
          <el-form-item label="课程描述">
            <el-input type="textarea" v-model="entityForm.description"></el-input>
          </el-form-item>
          <!--<el-form-item label="考试时间">-->
          <!--<el-date-picker-->
          <!--format="yyyy-MM-dd HH:mm"-->
          <!--type="datetime"-->
          <!--v-model="entityForm.examDate"-->
          <!--value-format="yyyy-MM-dd HH:mm"-->
          <!--&gt;-->
          <!--</el-date-picker>-->
          <!--</el-form-item>-->
          <!-- <el-form-item label="考试地点">
            <el-input v-model="entityForm.examLocation"></el-input>
          </el-form-item> -->
        </el-form>
        <span class="dialog-footer" slot="footer">
          <el-button @click="save" type="primary">确 定</el-button>
          <el-button @click="editing = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as api from "../../api/admin/course";
import * as teacherApi from "../../api/admin/teacher";

export default {
  name: "AdminCourse",
  data() {
    return {
      queryForm: {
        courseName: "",
        teacherName: "",
        college: "",
        type: ""
      },
      entityForm: {},
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      editing: false,
      teachers: [],
      courseDay: "",
      courseTime: "",
      courseLength: 0,
      total: 0,
      days: [
        {
          id: 1,
          name: "星期一"
        },
        {
          id: 2,
          name: "星期二",
        },
        {
          id: 3,
          name: "星期三",
        },
        {
          id: 4,
          name: "星期四",
        },
        {
          id: 5,
          name: "星期五",
        }
      ],
      times: [
        {
          id: 1,
          name: "第一、二节",
        },
        {
          id: 2,
          name: "第三、四节"
        },
        {
          id: 3,
          name: "第五、六节"
        },
        {
          id: 4,
          name: "第七、八节",
        }
      ]
    };
  },
  methods: {
    query() {
      api
        .getPageCount()
        .then(res => {
          this.pageCount = res.pageCount;
          this.getPage(this.pageIndex);
        });
    },
    getPage(pageIndex) {
      api
        .getPage(
          pageIndex,
          this.queryForm.courseName,
          this.queryForm.teacherName,
          this.queryForm.college,
          this.queryForm.type
        )
        .then(res => {
          console.log(res);
          this.tableData = res.rows;
          this.total = res.total;
        });
    },
    create() {
      this.entityForm = {
        id: -1,
        courseId: 0,
        courseName: "",
        type: "",
        teacherId: null,
        date: "",
        time: "",
        classroom: "",
        college: "",
        credit: 0,
        volume: 0,
        description: ""
      };
      // this.courseDay = 1;
      // this.courseTime = 1;
      this.courseLength = 2;
      this.editing = true;
    },
    edit(id) {
      api.get(id).then(res => {
        this.entityForm = res;
        this.editing = true;
      });
    },
    save() {
      if (this.entityForm.id === -1) {
        api.create(this.entityForm).then(() => {
          this.finishSave();
        });
      } else {
        api.update(this.entityForm).then(() => {
          this.finishSave();
        });
      }
    },
    finishSave() {
      this.$message.success("成功");
      this.getPage(this.pageIndex);
      this.editing = false;
    },
    deleteItem(id) {
      api.deleteItem(id).then(() => {
        this.$message.success("删除成功");
        this.getPage(this.pageIndex);
      });
    },
    getTeachers() {
      teacherApi.listName().then(res => {
        this.teachers = res;
      });
    }
  },
  created() {
    this.query();
    // this.getTeachers();
  }
};
</script>

<style scoped></style>
