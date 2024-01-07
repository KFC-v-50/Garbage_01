import { post, pureGet } from "../common/ajax";

export const login = (username, password, userType, selectedUniversity) =>
  post("/login", {
    id: username,
    password: password,
    userType: userType,
    university: selectedUniversity
  });
export const getLoginStatus = () => pureGet("/login/status");

export const logout = () => pureGet("/user/logout");
