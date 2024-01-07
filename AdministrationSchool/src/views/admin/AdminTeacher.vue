<template>
  <div class="teacher-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-user-plus"></i> 教师管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :span="2">
            <el-button @click="create" icon="el-icon-plus">创建</el-button>
          </el-col>
          <el-col :offset="13" :span="2">
            <el-input @keyup.enter.native="query" placeholder="教师" v-model="queryForm.name" />
          </el-col>
          <el-col :span="2">
            <el-input @keyup.enter.native="query" placeholder="学院" v-model="queryForm.college" />
          </el-col>
          <el-col :span="2">
            <el-input @keyup.enter.native="query" placeholder="工号" v-model="queryForm.staffId" />
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
          <!-- <el-table-column label="教师Id" prop="id" /> -->
          <el-table-column label="教师工号" prop="staffId" />
          <el-table-column label="教师姓名" prop="name" />
          <el-table-column label="所属学院" prop="college" />
          <el-table-column label="所属学校" prop="university" />
          <el-table-column label="上次登录" prop="loginTime" width="180px" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button @click="edit(scope.row.staffId)" size="mini" type="success">编辑
              </el-button>
              <el-button @click="deleteItem(scope.row.staffId)" size="mini" type="danger">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-dialog :visible.sync="editing" title="编辑" width="30%">
        <el-form :model="entityForm" label-width="70px" ref="form">
          <el-form-item label="教师工号">
            <el-input type="number" v-model="entityForm.staffId"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名">
            <el-input v-model="entityForm.name"></el-input>
          </el-form-item>
          <el-form-item label="所属学院">
            <el-input v-model="entityForm.college"></el-input>
          </el-form-item>
          <el-form-item label="权限">
            <el-select placeholder="请选择权限" v-model="entityForm.permission">
              <el-option :key="index" :label="item.name" :value="item.id" v-for="(item, index) in departments" />
            </el-select>
          </el-form-item>
          <el-form-item label="管理范围" v-if="entityForm.permission === 2 || entityForm.permission === 4">
            <el-input type="number" v-model="entityForm.className"></el-input>
          </el-form-item>
          <el-form-item label="登录密码">
            <el-input type="password" v-model="entityForm.password"></el-input>
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
import * as api from "../../api/admin/teacher";
import * as departmentApi from "../../api/admin/department";

export default {
  name: "AdminTeacher",
  data() {
    return {
      queryForm: {
        college: "",
        name: "",
        staffId: null
      },
      entityForm: {},
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      editing: false,
      departments: [
        {
          name: '教学秘书',
          id: 2
        },
        {
          name: '辅导员',
          id: 3
        },
        {
          name: '班主任',
          id: 4
        },
        {
          name: '管理员',
          id: 5
        }
      ],
      total: 0
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
          this.queryForm.college,
          this.queryForm.name,
          this.queryForm.staffId
        )
        .then(res => {
          console.log(res);
          this.tableData = res.rows;
          this.total = res.total;
        });
    },
    create() {
      this.entityForm = {
        staffId: null,
        name: "",
        college: "",
        university: "",
        permission: 0,
        password: "123456",
        loginTime: null,
        className: null,
      };
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
    getDepartments() {
      departmentApi.listName().then(res => {
        this.departments = res;
      });
    }
  },
  created() {
    this.query();
    // this.getDepartments();
  }
};
</script>

<style scoped></style>
