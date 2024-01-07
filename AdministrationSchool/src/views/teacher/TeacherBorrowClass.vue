<template>
  <div class="course-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-edit"></i> 教室申请
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column label="教室Id" prop="classroom" />
          <el-table-column label="类型" prop="type" />
          <el-table-column label="情况" prop="university" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <div>
                <el-button @click="showDialog(scope.row.classroom)" size="mini" type="danger">申请</el-button>
                <el-dialog :visible.sync="dialogVisible" title="教室申请表" width="30%">
                  <el-form label-width="80px">
                    <el-form-item label="教室ID号">
                      <el-input v-model="form.classroom"></el-input>
                    </el-form-item>
                    <el-form-item label="课程ID号">
                      <el-input v-model="form.courseID"></el-input>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitForm">提交</el-button>;
                  </div>
                </el-dialog>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "TeacherBorrowClass",
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      form: {
        classroom: undefined,
        courseID: undefined
      }
    };
  },
  methods: {
    showDialog(classroom) {
      this.form.classroom = classroom;
      this.dialogVisible = true;
    },
    submitForm() {
      const { classroom, courseID } = this.form;

      axios
        .post("http://localhost:8085/classroom/change", {
          classroom,
          courseID
        }, {
          headers: {
            token: localStorage.getItem("token")
          },
        })
        .then(() => {
          this.$message.success("提交申请成功，请等待审核")
          console.log("提交成功");
        })
        .catch(error => {
          console.error("提交失败", error);
        });

      this.dialogVisible = false;
    },
    getList() {
      axios
        .get("http://localhost:8085/classroom/find", {
          headers: {
            token: localStorage.getItem("token")
          },
        })
        .then(response => {
          console.log(response.data);
          this.tableData = response.data.data;
        })
        .catch(error => {
          console.error("获取表格数据失败", error);
        });
    }
  },
  created() {
    this.getList();
  }
};
</script>

<style scoped></style>
