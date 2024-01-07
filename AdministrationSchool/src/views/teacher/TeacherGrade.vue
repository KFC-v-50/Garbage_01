<template>
  <div class="grade-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-edit"></i> 成绩录入
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :offset="15" :span="3">
            <el-input @keyup.enter.native="query" placeholder="课程名" v-model="queryForm.courseName" />
          </el-col>
          <el-col :span="3">
            <el-input @keyup.enter.native="query" placeholder="学生名" v-model="queryForm.studentName" />
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
          <el-table-column label="选课Id" prop="courseId" />
          <el-table-column label="课程名" prop="courseName" />
          <el-table-column label="学号" prop="stuId" />
          <el-table-column label="学生姓名" prop="studentName" />
          <el-table-column label="日常分" prop="ordinary" />
          <el-table-column label="笔试分" prop="ending" />
          <el-table-column label="成绩" prop="score" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button @click="edit(scope.row.courseId, scope.row.stuId)" size="mini" type="success">打分
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-dialog :visible.sync="editing" title="编辑" width="30%">
        <el-form :model="entityForm" label-width="70px" ref="form">
          <el-form-item label="选课Id">
            <el-input disabled type="number" v-model="entityForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="日常成绩">
            <el-input type="number" v-model="entityForm.ordinary" @input="handleNumberInput('ordinary')"></el-input>
          </el-form-item>
          <el-form-item label="笔试成绩">
            <el-input type="number" v-model="entityForm.ending" @input="handleNumberInput('ending')"></el-input>
          </el-form-item>
          <el-form-item label="总成绩">
            <el-input type="number" v-model="entityForm.score" @input="handleNumberInput('score')"></el-input>
          </el-form-item>
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
import * as api from "../../api/teacher/grade";

export default {
  name: "TeacherGrade",
  data() {
    return {
      queryForm: {
        courseName: "",
        studentName: ""
      },
      entityForm: {},
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      total: 1,
      pageIndex: 1,
      editing: false
    };
  },
  methods: {
    query() {
      api
        .getPageCount(this.queryForm.courseName, this.queryForm.studentName)
        .then(res => {
          this.pageCount = res.pageCount;
          this.total = res.total;
          this.getPage(this.pageIndex);
        });
    },
    getPage(pageIndex) {
      api
        .getPage(
          pageIndex,
          this.queryForm.courseName,
          this.queryForm.studentName
        )
        .then(res => {
          this.tableData = res.rows;
        });
    },
    edit(courseId, stuId) {
      api.get(courseId, stuId).then(res => {
        this.entityForm = res;
        this.editing = true;
      });
    },
    save() {
      api.update(this.entityForm).then(() => {
        this.$message.success("成功");
        this.getPage(this.pageIndex);
        this.editing = false;
      });
    },
    handleNumberInput(field) {
      // 使用Number()函数将输入的字符串转换为数字
      this.entityForm[field] = Number(this.entityForm[field]);
    },
  },
  created() {
    this.query();
  }
};
</script>

<style scoped></style>
