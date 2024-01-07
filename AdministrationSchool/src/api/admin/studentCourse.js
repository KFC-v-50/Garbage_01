import * as ajax from "../../common/ajax";

export const get = (id, stuId) => ajax.get("/selection/" + id + "/" + stuId);

export const create = entity => ajax.post("/admin/addClassToStu", entity);

export const deleteItem = (id, stuId) => ajax.pureDelete("/selection/" + id + "/" + stuId);

export const update = entity => ajax.put("/selection", entity);

export const getPageCount = (className, courseName, studentName) =>
  ajax.get("/selection/page", {
    className: className,
    courseName: courseName,
    studentName: studentName
  });

export const getPage = (index, className, courseName, studentName) =>
  ajax.get("/selection/page", {
    page: index,
    className: className,
    courseName: courseName,
    studentName: studentName
  });

export const pageSize = 20;
