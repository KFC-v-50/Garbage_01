import * as ajax from "../../common/ajax";

export const get = () => ajax.pureGet("/student");

export const update = entity => ajax.put("/student/update", entity);
