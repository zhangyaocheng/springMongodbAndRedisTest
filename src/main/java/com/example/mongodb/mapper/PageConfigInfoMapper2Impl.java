package com.example.mongodb.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.mongodb.pojo.PageConfigInfo;
import com.example.mongodb.pojo.PageUIElement;
import com.example.mongodb.util.PageLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PageConfigInfoMapper2Impl implements PageConfigInfoMapper2{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<PageConfigInfo> findAll(int pageNum, int size) {

        Query query = new Query();
        pageNum = pageNum<=1?0:pageNum-1;
        Pageable pageable = PageRequest.of(pageNum,size);

//        List<PageConfigInfo> list = mongoTemplate.find(query,PageConfigInfo.class,"PageConfigInfo");
        Page<PageConfigInfo> page =(Page<PageConfigInfo>) getPageConfigInfoPage(query,pageable);
        log.info("page getsize():"+page.getSize()+" page.getNum():"+page.getNumber()+"\n" +
                "page.getTotoalPage:"+page.getTotalPages()+"\npage.getTotoalElement:"+page.getNumberOfElements());

        return page;
    }

    /**
     * 使用若干个字段进行查询
     * 使用projectCode
     * module
     * pageName
     * creator
     * @param json
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public Page<PageConfigInfo> findByParameter(JSONObject json,int pageNum, int size) {
        Query query = new Query();
        if (json.getString("projectCode")==null) json.put("projectCode","");
        query.addCriteria(Criteria.where("projectCode").regex(json.getString("projectCode")));
        if (json.getString("module")==null) json.put("module","");
        query.addCriteria(Criteria.where("module").regex(json.getString("module")));
        if (json.getString("pageName")==null) json.put("pageName","");
        query.addCriteria(Criteria.where("pageName").regex(json.getString("pageName")));
        if (json.getString("creator")==null) json.put("creator","");
        query.addCriteria(Criteria.where("creator").regex(json.getString("creator")));

        pageNum = pageNum<=1?0:pageNum-1;
        Pageable pageable = PageRequest.of(pageNum,size);

        Page<PageConfigInfo> page = (Page<PageConfigInfo>) getPageConfigInfoPage(query,pageable);


        return page;
    }

    /**
     * 对指定数据库的类进行分页
     * 目前mongotemplate只能使用这种方式进行分页
     * @param query
     * @param pageable
     * @return
     */
    public Page<PageConfigInfo> getPageConfigInfoPage(Query query, Pageable pageable){


        int count = (int) mongoTemplate.count(query, PageConfigInfo.class, "PageConfigInfo");
        query.with(pageable);
        List<PageConfigInfo> items = mongoTemplate.find(query,PageConfigInfo.class,"PageConfigInfo");
        return PageableExecutionUtils.getPage(items,pageable,()->count);

    }

    /**
     * 对aggregation结果分页
     * @param aggregation
     * @param pageable
     * @return
     */
    public Page<PageConfigInfo> getPageConfigInfoPageByAggregation(Aggregation aggregation, Pageable pageable){

        // 通过某种方式直接获取到想要查询的结果数量 count 这样就可以利用PageableExecutionUtils.getPage方法获取分页结果了
        // 其实，repositry的分页方案就是对这种直接分页方法的封装而已

        // 获取聚合查询的结果
        List<PageUIElement> lists = mongoTemplate.aggregate(aggregation,PageConfigInfo.class, PageUIElement.class).getMappedResults();

        return null;
    }
}
