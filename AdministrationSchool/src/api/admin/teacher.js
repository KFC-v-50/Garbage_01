import * as ajax from "../../common/ajax";

export const get = id => ajax.get("/teacher/" + id);

export const create = entity => ajax.post("/teaRegister", entity);

export const deleteItem = id => ajax.pureDelete("/admin/teacher/" + id);

export const update = entity => ajax.patch("/teacher/update", entity);

export const getPageCount = () =>
  ajax.get("/teacher/page", {
    page: 1
  });

export const getPage = (index, college, name, staffId) =>
  ajax.get("teacher/page", {
    page: index,
    college: college,
    name: name,
    staffId: staffId
  });

export const listName = () => ajax.pureGet("/admin/teacher/names");

export const pageSize = 20;
