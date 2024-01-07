import * as ajax from "../../common/ajax";

export const get = (courseId, stuId) => ajax.pureGet(`/teacher/scoreInfo/${courseId}/${stuId}`);

export const update = entity => ajax.patch("/teacher/scoring", entity);

export const getPageCount = (courseID, studentID) =>
  ajax.get("/teacher/grade", {
    page: 1,
    pageSize: pageSize,
    courseId: courseID,
    studentId: studentID
  });

export const getPage = (index, courseID, studentID) =>
  ajax.get("/teacher/grade", {
    page: index,
    pageSize: pageSize,
    courseId: courseID,
    studentId: studentID
  });

export const pageSize = 1;
