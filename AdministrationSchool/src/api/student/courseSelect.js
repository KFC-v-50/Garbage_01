import * as ajax from "../../common/ajax";

export const select = data => ajax.post("/student/selectCourse", data);

export const getPageCount = (courseName, teacherName) =>
  ajax.get("/course", {
    courseName: courseName,
    teacherName: teacherName
  });

export const getPage = (index, courseName, teacherName) =>
  ajax.get("/course", {
    page: index,
    pageSize: 1,
    courseName: courseName ? courseName : null,
    teacherName: teacherName ? teacherName : null
  });

export const pageSize = 1;
