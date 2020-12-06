package com.example.mongodb.util;

import com.example.mongodb.pojo.PageConfigInfo;
import com.example.mongodb.pojo.PageResult;
import org.springframework.data.domain.Page;

public class PageUitls {

    public static PageResult getMongodbData(Page<?> pageConfigInfos){

        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageConfigInfos.getNumber()+1);
        pageResult.setPageSize(pageConfigInfos.getSize());
        pageResult.setTotalNum(pageConfigInfos.getTotalPages());
        pageResult.setTotalSize((int) pageConfigInfos.getTotalElements());
        pageResult.setData(pageConfigInfos.getContent());

        return pageResult;
    }

}
