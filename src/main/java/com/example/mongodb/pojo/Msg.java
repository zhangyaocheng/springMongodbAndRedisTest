package com.example.mongodb.pojo;

import lombok.Data;

/**
 * 返回给前端的内容
 */

@Data
public class Msg<T> {

    private String status;
    private boolean Ok;
    private String errMessage;
    private String exceptMessage;
    private T data;

}
