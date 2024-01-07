<template>
  <div class="grade-analysis-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-book"></i> 成绩分析
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :offset="17" :span="3">
            <el-input @keyup.enter.native="query" placeholder="课程ID" v-model="queryForm.courseId" />
          </el-col>
          <el-col :span="3">
            <el-button @click="query" icon="el-icon-search" type="primary">搜索</el-button>
          </el-col>
        </el-row>
      </div>

      <div class="chart">
        <el-row>
          <el-col :span="24">
            <!-- Use ref attribute to reference the chart container -->
            <div ref="echartsChart" style="height: 300px;"></div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import echarts from "echarts";

export default {
  name: "GradeAnalysis",
  data() {
    return {
      queryForm: {
        courseId: ""
      },
      echartsInstance: null
    };
  },
  methods: {
    query() {
      axios
        .get("http://localhost:8085/student/gradeAnalysis", {
          params: {
            courseId: this.queryForm.courseId
          },
          headers: {
            token: localStorage.getItem("token")
          }
        })
        .then(response => {
          const analysisData = response.data.data;

          this.renderECharts(analysisData);
        })
        .catch(error => {
          console.error(error);
        });
    },

    renderECharts(analysisData) {
      // Initialize ECharts instance if not already
      if (!this.echartsInstance) {
        this.echartsInstance = echarts.init(this.$refs.echartsChart);
      }

      // Check if analysisData is defined and has the expected structure
      if (analysisData && analysisData.length > 0) {
        // Extract data from analysisData
        const averageScores = analysisData.map(item => item.averageScore.toFixed(2));
        const passRates = analysisData.map(item => item.passRate);

        // ECharts option configuration
        const option = {
          xAxis: {
            type: "category",
            data: ["2019年", "2020年", "2021年", "2022年", "2023年"]
          },
          yAxis: {
            type: "value",
            min: 0,
            max: 100
          },
          legend: {
            data: ["平均分", "及格率"]
          },
          series: [
            {
              name: "平均分",
              type: "bar",
              data: averageScores
            },
            {
              name: "及格率",
              type: "bar",
              data: passRates
            }
          ]
        };

        // Set the option and update the chart
        this.echartsInstance.setOption(option);
      } else {
        // Log an error or handle the case when analysisData is empty or not in the expected format
        console.error("Invalid analysisData format:", analysisData);
      }
    }

  }
};
</script>

<style scoped>
.grade-analysis-wrap {
  margin: 20px;
}

.query-form {
  margin-bottom: 20px;
}

.chart {
  height: 300px;
}
</style>
