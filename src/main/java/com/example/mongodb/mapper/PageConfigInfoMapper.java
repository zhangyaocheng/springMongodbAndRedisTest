package com.example.mongodb.mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.mongodb.pojo.PageConfigInfo;
import com.example.mongodb.pojo.PageUIElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PageConfigInfoMapper extends MongoRepository<PageConfigInfo,String>{

//    @Query
    //"{$match:?#{{'_id':ObjectId([0].getString('id'))}}},{$project:{'pageInfos':1}}," +
    //            "{$unwind:'$projectInfos'},{$match:{'pageInfos.description':?#{{$regex:[0].getString('description')}}," +
    //            "'pageInfos.xpath':{$elemMatch:?#{{$eq:[0].getString('xpath')}}}}}"
    // 对于xpath只能使用精准匹配的方式，无法使用动态匹配，因此需要再写一个接口才行
    // 对于aggregation的返回结果无法使用分页进行处理
    @Aggregation(pipeline = {"{$match:{'_id':ObjectId('5fcc7747b3a1a91f74003be2')}}","{$project:{'pageInfos':1}}",
            "{$unwind:'$pageInfos'}","{$match:{'pageInfos.description':?#{{$regex:[0].getString('description')}}," +
            "'pageInfos.xpath':{$elemMatch:?#{{$eq:[0].getString('xpath')}}}}}"})
    List findByIdWithParameter(JSONObject json,Pageable pageable);

//    Page<PageConfigInfo> findAll(Pageable pageable);
    Page<PageConfigInfo> findByCreator(String creator, Pageable pageable);
}
