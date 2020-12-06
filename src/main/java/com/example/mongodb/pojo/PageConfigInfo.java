package com.example.mongodb.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 页面配置信息
 */
@Data
@Document(collection = "PageConfigInfo")
public class PageConfigInfo {

    private String id;
    private String projectCode;
    private String module;
    private String pageName;
    private String creator;
    private List<PageUIElement> pageInfos;

}
