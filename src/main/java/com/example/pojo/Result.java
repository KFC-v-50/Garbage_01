package com.example.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

/**
 * 返回结果封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable{
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200,"success", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg,null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(401, msg,null);
    }
}