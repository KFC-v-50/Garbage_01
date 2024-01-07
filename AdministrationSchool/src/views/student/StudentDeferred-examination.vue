<template>
  <div class="grade-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-edit"></i> 申请缓考
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :offset="15" :span="3">
            <el-input @keyup.enter.native="query" placeholder="课程号" v-model="queryForm.courseId" />
          </el-col>
          <el-col :span="3">
            <el-input @keyup.enter.native="query" placeholder="学生号" v-model="queryForm.studentId" />
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
          <el-table-column label="学号" prop="stuId" />
          <el-table-column label="审核人" prop="administrator" />
          <el-table-column label="学年" prop="year" />
          <el-table-column label="学期" prop="trimesters" />
          <el-table-column label="原因" prop="reason" />
          <el-table-column align="center" label="提交" prop="submit" width="200px">
            <template slot-scope="scope">
              <el-button @click="edit(scope.row.courseId, scope.row.stuId)" size="mini" type="success">申请缓考
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-dialog :visible.sync="editing" title="编辑" width="30%">
        <el-form :model="entityForm" label-width="70px" ref="form">
          <el-form-item label="选课Id">
            <el-input disabled type="Integer" v-model="entityForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="审核人">
            <el-input type="Integer" v-model="entityForm.administrator" ></el-input>
          </el-form-item>
          <el-form-item label="学年">
            <el-input type="String" v-model="entityForm.year" ></el-input>
          </el-form-item>
          <el-form-item label="学期">
            <el-input type="Integer" v-model="entityForm.trimesters" ></el-input>
          </el-form-item>
          <el-form-item label="原因">
            <el-input type="String" v-model="entityForm.reason" ></el-input>
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
import * as api from "../../api/student/de-exam";

export default {
  name: "StudentDeferred-examination",
  data() {
    return {
      queryForm: {
        courseId: "",
        studentId: ""
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
        .getPageCount(this.queryForm.courseId, this.queryForm.studentId)
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
          this.queryForm.courseId,
          this.queryForm.studentId
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
    // handleNumberInput(field) {
    //   // 使用Number()函数将输入的字符串转换为数字
    //   //this.entityForm[field] = Number(this.entityForm[field]);
    // },
  },
  created() {
    this.query();
  }
};
</script>

<style scoped></style>
