import * as ajax from "../../common/ajax";

export const get = (courseId, stuId) => ajax.pureGet(`/teacher/scoreInfo/${courseId}/${stuId}`);

export const update = entity => ajax.put("/teacher/scoring", entity);

export const getPageCount = (courseName, studentName) =>
  ajax.get("/teacher/grade", {
    page: 1,
    pageSize: pageSize,
    courseName: courseName,
    studentName: studentName
  });

export const getPage = (index, courseName, studentName) =>
  ajax.get("/teacher/grade", {
    page: index,
    pageSize: pageSize,
    courseName: courseName,
    studentName: studentName
  });

export const pageSize = 1;
