package com.example.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    Apply_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    CONTENT_NOT_NULL(506, "评论为空"),
    FILE_TYPE_ERROR(507, "图片格式不对,要.png"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    AUTHORITY_ERROR(600, "权限不足"),
    Cover_EXIST(601, "申请已存在"),
    Email_Not_Exist(602,"邮箱未注册"),
    Photo_Exist(603,"图片已存在"),
    TAG_EXIST(513, "标签已存在"),
    Internal_Error(604,"内部错误" ),
    Invalid_User(605,"用户无法识别" ),
    Invalid_Param(606,"参数无法识别" );


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
