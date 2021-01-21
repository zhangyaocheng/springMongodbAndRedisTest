package com.example.mongodb.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.example.mongodb.mapper.PageConfigInfoMapper;
import com.example.mongodb.mapper.PageConfigInfoMapper2;
import com.example.mongodb.pojo.Msg;
import com.example.mongodb.pojo.PageConfigInfo;
import com.example.mongodb.pojo.PageResult;
import com.example.mongodb.pojo.PageUIElement;
import com.example.mongodb.util.PageUitls;
import com.mongodb.internal.operation.AggregateOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置信息服务
 */
@Slf4j
@Service
public class PageConfigService {

    @Autowired
    PageConfigInfoMapper pageConfigInfoMapper;

    @Autowired
    PageConfigInfoMapper2 pageConfigInfoMapper2;

    @Autowired
    MongoTemplate mongoTemplate;

    public Msg findInnerDataByParameter(JSONObject json, int pageNum, int size){
        Msg result = new Msg();
        result.setOk(false);
        result.setStatus("500");
        result.setData(null);

        try {

            if (json.getString("id")==null || "".equals(json.getString("id"))){
                result.setErrMessage("id缺失或者值为空");
                return result;
            }
            if (json.getString("description")==null){
                json.put("description","");
            }
            if (json.getString("xpath")==null){
                json.put("xpath","");
            }

            pageNum = pageNum<=1?0:pageNum-1;
            Pageable pageable = PageRequest.of(pageNum,size);
//            Page<PageUIElement> page = pageConfigInfoMapper.findByIdWithParameter(json,pageable);
//            result.setData(PageUitls.getMongodbData(page));
//            log.info("page:"+page);
            List list = pageConfigInfoMapper.findByIdWithParameter(json,pageable);
            log.info("List:"+list);
            result.setData(list);
            result.setStatus("200");
            result.setOk(true);

        }catch (Exception e){
            result.setExceptMessage("通过参数获取内部数据异常");
            log.error("通过参数获取内部数据异常",e);
        }

        return result;
    }

    public Msg findAllPageConfigInfo(int pageNum, int size){
        Msg resutl = new Msg();
        resutl.setOk(false);
        resutl.setData(null);
        resutl.setStatus("500");

        try {

            pageNum = pageNum<=1?0:pageNum-1;
            Pageable pageable = PageRequest.of(pageNum,size);
//            Page<PageConfigInfo> page = pageConfigInfoMapper.findAll(pageable);
            Page<PageConfigInfo> page = pageConfigInfoMapper2.findAll(pageNum,size);
            resutl.setData(PageUitls.getMongodbData(page));
            resutl.setOk(true);
            resutl.setStatus("200");


        }catch (Exception e){

            resutl.setExceptMessage("获取所有页面配置信息异常");
            log.error("获取所有页面配置信息异常",e);
        }

        return resutl;
    }

    /**
     * 从数据库中获取所有非重复的module类容
     * @param module
     * @return
     */
    public Msg findAllDistincModule(String module){

        Msg result = new Msg();
        result.setStatus("500");
        result.setOk(false);
        result.setData(null);

        try {
            if (module==null || module.equals("")){
                result.setErrMessage("module缺失或者值为空");
                return result;
            }

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("module").regex(module)),
                    Aggregation.group("module"),
                    Aggregation.project("module").and("module").as("module"));

            AggregationResults<PageConfigInfo> aggregationResults = mongoTemplate.aggregate(
                    aggregation,"PageConfigInfo",PageConfigInfo.class);
            List<PageConfigInfo> lists = aggregationResults.getMappedResults();
            List<String> modules = new ArrayList<>();
            for (PageConfigInfo pageConfigInfo: lists){
                log.info("pageConfigInfo:"+pageConfigInfo);
                log.info("module:"+pageConfigInfo.getId());
                modules.add(pageConfigInfo.getId());
            }

            result.setData(modules);
            result.setOk(true);
            result.setStatus("200");
        }catch (Exception e){
            result.setExceptMessage("获取所有非重复Module异常");
            log.error("获取所有非重复Module异常",e);
        }

        return result;
    }

}
