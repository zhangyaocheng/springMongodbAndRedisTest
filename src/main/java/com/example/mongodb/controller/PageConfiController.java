package com.example.mongodb.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mongodb.pojo.Msg;
import com.example.mongodb.service.PageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/config")
@RestController
public class PageConfiController {

    @Autowired
    PageConfigService pageConfigService;

    @PostMapping("/findInnerDataByParameter")
    public Msg findInnerDataByParameter(@RequestBody JSONObject json,
                                        @RequestParam(value = "pageNum",defaultValue = "0") int pageNum,
                                        @RequestParam(value = "size",defaultValue = "10") int size){
        return pageConfigService.findInnerDataByParameter(json,pageNum,size);
    }

    @GetMapping("/listPageConfigInfo")
    public Msg findAllPageConfigInfo( @RequestParam(value = "pageNum",defaultValue = "0") int pageNum,
                                      @RequestParam(value = "size",defaultValue = "10") int size){
        return pageConfigService.findAllPageConfigInfo(pageNum,size);
    }


    @ResponseBody
    @GetMapping("/findAllDistinctModule")
    public Msg findAllDistinctModule(@RequestParam(value = "module") String module){
        return pageConfigService.findAllDistincModule(module);
    }

}
