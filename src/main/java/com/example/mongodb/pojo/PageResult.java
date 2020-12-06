package com.example.mongodb.pojo;

import lombok.Data;

import java.util.List;

/**
 * 分页返回信息
 */
@Data
public class PageResult<T> {

    private Integer pageNum; // 第几页
    private Integer pageSize; // 每一页多少内容
    private Integer totalSize; // 总共多少数据
    private Integer totalNum; // 总页数
    private List<T> data;

}
