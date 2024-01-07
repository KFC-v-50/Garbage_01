<template>
    <div class="course-wrap">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-fa fa-edit"></i> 校园论坛
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-card class="table">
                <div slot="header" class="clearfix">
                    <span>校园论坛</span>
                    <el-button @click="addshow" style="float: right; padding: 3px 0" type="text">发表言论</el-button>
                </div>
                <el-table :data="tableData" stripe>
                    <el-table-column label="ID" prop="id" />
                    <el-table-column label="名称" prop="name" />
                    <el-table-column label="身份" prop="userType">
                        <template slot-scope="scope">
                            {{ userTypes(scope.row.userType) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="论坛内容" prop="comment" />
                    <el-table-column label="所属校园" prop="university" />
                </el-table>
            </el-card>
        </div>
        <el-dialog title="发表言论" :visible.sync="dialogTableVisible">
            <el-form class="form" label-width="80px">
                <el-form-item label="发表言论">
                    <el-input type="text" placeholder="请输入你的言论" v-model="comment" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="sub">发表</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
  
<script>
import * as api from "../../api/forum.js";

export default {
    name: "StudentCourse",
    data() {
        return {
            tableData: [],
            dialogTableVisible: false,
            comment: ''
        };
    },
    methods: {
        getList() {
            api.forumList().then(res => {
                console.log(res)
                this.tableData = res
            });
        },
        addshow() {
            this.dialogTableVisible = true
        },
        sub() {
            const { comment } = this
            api.forum({ comment }).then(res => {
                console.log(res)
                this.dialogTableVisible = false
                this.getList()
            })
        },
        userTypes(e) {
            switch (e) {
                case 1:
                    return "学生"
                case 2:
                    return "老师"
                case 3:
                    return "老师2"
                case 4:
                    return "管理员"
            }
        }
    },
    created() {
        this.getList();
    }
};
</script>
  
<style scoped></style>
  