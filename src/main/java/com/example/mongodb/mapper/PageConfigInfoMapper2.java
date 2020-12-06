package com.example.mongodb.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.mongodb.pojo.PageConfigInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface PageConfigInfoMapper2 {

    Page<PageConfigInfo> findAll(int pageNum, int size);
    Page<PageConfigInfo> findByParameter(JSONObject json, int pageNum, int size);
//    Page<PageConfigInfo> findInnerDataByParameter(JSONObject json, int pageNum, int size);

}
