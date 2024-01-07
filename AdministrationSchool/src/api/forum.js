import { post, pureGet } from "../common/ajax";

export const forum = (data) =>
    post('/forum/postcomment', data)


export const forumList = () =>
    pureGet('/forum/findAllComment')

