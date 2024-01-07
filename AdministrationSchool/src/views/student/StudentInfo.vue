<template>
  <div class="info-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-id-badge"></i> 信息维护
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <el-form :model="entityForm" class="info-form" label-width="80px">
        <el-form-item label="学号">
          <el-input disabled v-model="entityForm.stuId"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input disabled v-model="entityForm.name"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input disabled v-model="entityForm.university"></el-input>
        </el-form-item>
        <el-form-item label="学院">
          <el-input disabled v-model="entityForm.college"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input disabled v-model="entityForm.major"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input disabled v-model="entityForm.className"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="entityForm.grand"></el-input>
        </el-form-item>

        <el-form-item label="密码">
          <el-input type="password" v-model="entityForm.password"></el-input>
        </el-form-item>
        <el-form-item label="操作">
          <el-button @click="update" type="primary">确认更新</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import * as api from "../../api/student/info";

export default {
  name: "StudentInfo",
  data() {
    return {
      entityForm: {
        stuId: "",
        name: "",
        major: "",
        college: "",
        className: "",
        university: "",
        grand: "",
        password: ""
      }
    };
  },
  methods: {
    showMessage() {
      this.$message.success("这是一个成功消息");
    },
    get() {
      api.get().then(res => {
        console.log(res);
        this.entityForm = res;
      });
    },
    update() {
      api.update(this.entityForm).then(() => {
        this.$message.success("更新信息成功!");
      });
    }
  },
  created() {
    this.get();
  }
};
</script>

<style scoped>
.info-form {
  min-width: 400px;
  width: 35%;
  margin: auto;
}
</style>
