package com.example.mongodb.service;

import com.alibaba.fastjson.JSONObject;
import com.example.mongodb.mapper.PageConfigInfoMapper;
import com.example.mongodb.mapper.PageConfigInfoMapper2;
import com.example.mongodb.pojo.Msg;
import com.example.mongodb.pojo.PageConfigInfo;
import com.example.mongodb.pojo.PageResult;
import com.example.mongodb.pojo.PageUIElement;
import com.example.mongodb.util.PageUitls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

}
