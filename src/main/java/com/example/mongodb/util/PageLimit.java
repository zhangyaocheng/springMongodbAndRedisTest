package com.example.mongodb.util;

import com.example.mongodb.pojo.PageConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现，使用mongotemplate进行分页
 */
@Component
public class PageLimit {

    @Autowired
    MongoTemplate mongoTemplate;



}
