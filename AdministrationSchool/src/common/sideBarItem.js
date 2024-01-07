import UserType from "./userType";
import Permission from "./permission";

let SideBarItem = {};

SideBarItem.items = [
  {
    icon: "el-icon-user-solid",
    index: "/student",
    title: "学生首页",
    userType: UserType.student,
    permission: Permission.no,
  },
  {
    icon: "el-icon-reading",
    index: "/student/course/select",
    title: "选修课程",
    userType: UserType.student,
    permission: Permission.no,
  },
  {
    icon: "el-icon-postcard",
    index: "/student/course",
    title: "学生课程",
    userType: UserType.student,
    permission: Permission.no,
  },
  {
    icon: "el-icon-film",
    index: "/student/timetable",
    title: "课表查询",
    userType: UserType.student,
    permission: Permission.no,
  },
  // {
  //   icon: "el-icon-fa fa-eye",
  //   index: "/student/exam",
  //   title: "考试查询",
  //   userType: UserType.student,
  //   permission: Permission.no
  // },
  {
    icon: "el-icon-notebook-2",
    index: "/student/score",
    title: "成绩查询",
    userType: UserType.student,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-comment",
    index: "/student/info",
    title: "信息维护",
    userType: UserType.student,
    permission: Permission.no,
  },
  {
    icon: "el-icon-finished",
    index: "/student/analysis",
    title: "成绩分析",
    userType: UserType.student,
    permission: Permission.no,
  },

  {
    icon: "el-icon-s-custom",
    index: "/teacher",
    title: "教师首页",
    userType: UserType.teacher,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-grid",
    index: "/teacher/course",
    title: "授课查询",
    userType: UserType.teacher,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-management",
    index: "/teacher/timetable",
    title: "教师课表",
    userType: UserType.teacher,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-check",
    index: "/teacher/grade",
    title: "成绩录入",
    userType: UserType.teacher,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-claim",
    index: "/teacher/borrowClass",
    title: "教室申请",
    userType: UserType.teacher,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-custom",
    index: "/admin",
    title: "管理员首页",
    userType: UserType.admin,
    permission: Permission.no,
  },
  {
    icon: "el-icon-s-tools",
    index: "/admin/department",
    title: "系管理",
    userType: UserType.admin,
    // permission: Permission.department
  },
  {
    icon: "el-icon-help",
    index: "/admin/major",
    title: "专业管理",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-s-grid",
    index: "/admin/class",
    title: "班级管理",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-user",
    index: "/admin/student",
    title: "学生管理",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-user-solid",
    index: "/admin/teacher",
    title: "教师管理",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-s-platform",
    index: "/admin/course",
    title: "课程管理",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-edit-outline",
    index: "/admin/student/course",
    title: "选课修改",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-setting",
    index: "/admin/admin",
    title: "管理员管理",
    userType: UserType.admin,
    // permission: Permission.department
  },
  {
    icon: "el-icon-date",
    index: "/admin/confirm",
    title: "变更教室管理",
    userType: UserType.admin,
    permission: Permission.department,
  },
  {
    icon: "el-icon-chat-line-round",
    index: "/forun",
    title: "校园论坛",
    userType: UserType.student || UserType.teacher || UserType.admin,
    permission: Permission.no,
  },
];

export default SideBarItem;
