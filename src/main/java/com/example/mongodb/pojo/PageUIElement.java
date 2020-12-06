package com.example.mongodb.pojo;

import lombok.Data;

import java.util.List;

/**
 * 页面配置元素信息
 */
@Data
public class PageUIElement {

    private String description;
    private String frame;
    private String type;
    private List<String> xpath;

}
