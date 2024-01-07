import * as ajax from "../../common/ajax";

export const get = id => ajax.get("/course/" + id);

export const create = entity => ajax.post("/course", entity);

export const deleteItem = id => ajax.pureDelete("/admin/course/" + id);

export const update = entity => ajax.put("/admin/resetClass", entity);

export const getPageCount = () =>
  ajax.get("/course");

export const getPage = (index, courseName, teacherName, college, type) =>
  ajax.get("/course", {
    page: index,
    courseName: courseName,
    teacherName: teacherName,
    college: college,
    type: type
  });

export const listName = () => ajax.pureGet("/course/list");

export const pageSize = 20;
