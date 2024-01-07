<template>
  <div class="login-wrap">
    <div class="login-form">
      <div class="form-title">教务管理系统</div>
      <el-form :model="formData" :rules="rules" class="form-content" label-width="0px" ref="form">
        <el-form-item prop="username">
          <el-input placeholder="学号/工号/用户名" v-model="formData.username" prefix-icon="el-icon-user"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input @keyup.enter.native="submit()" placeholder="密码" type="password" v-model="formData.password"
            prefix-icon="el-icon-edit"></el-input>
        </el-form-item>

        <el-form-item prop="userType">
          <el-radio-group v-model="formData.userType">
            <el-radio label="1">学生</el-radio>
            <el-radio label="2">教师</el-radio>
            <el-radio label="3">辅导员</el-radio>
            <el-radio label="4">管理员/教秘</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item prop="selectedUniversity">
          <el-select v-model="formData.selectedUniversity" placeholder="请选择">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>

        <div class="login-btn" v-loading="this.$store.state.loading">
          <el-button @click="submit()" type="primary">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { login } from "../api/user";

export default {
  data() {
    return {
      options: [
        { value: '华中师范大学', label: '华中师范大学' },
        { value: '武汉理工大学', label: '武汉理工大学' },
      ],
      formData: {
        username: "",
        password: "",
        userType: "1",
        selectedUniversity: "华中师范大学",
      },
      rules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        userType: [{ required: true, message: "请选择用户类型", trigger: "blur" }],
        selectedUniversity: [{ required: true, message: "请选择学校", trigger: "blur" }],
      },
    };
  },
  methods: {
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          console.log(this.formData)
          login(
            this.formData.username,
            this.formData.password,
            this.formData.userType,
            this.formData.selectedUniversity
          ).then(res => {
            console.log(res)
            localStorage.setItem('token', res.token);
            this.$message.success("登录成功: " + res.username);
            this.$store.commit("login", res);
            this.$router.push({ name: "container" });
          });
        }
      });
    },
  },

};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url("../assets/login-background.jpg");
  background-size: 100% 100%;
}

.form-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.login-form {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 400px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(0, 0, 0, 0.6);
  overflow: hidden;
}

.form-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
}

.el-radio-group {
  margin-bottom: 20px;
}

.el-radio {
  color: #fff;
  margin-right: 10px;
}

.el-form-item__label {
  color: #ccc;
}

.el-select {
  width: 100%;
  border: 1px solid #ccc;
}

.el-input__inner,
.el-input__inner:hover,
.el-input__inner:focus {
  border-color: #ccc;
}

.el-select-dropdown {
  border-radius: 0;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.el-select-dropdown .el-select-dropdown__item {
  padding: 10px 20px;
  font-size: 14px;
  color: #333;
}

.el-select-dropdown .el-scrollbar__wrap {
  max-height: 200px;
}
</style>


