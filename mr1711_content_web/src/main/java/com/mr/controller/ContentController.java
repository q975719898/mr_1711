package com.mr.controller;

import com.mr.JsonUtils;
import com.mr.model.TbContentUtil;
import com.mr.service.Impl.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by czl on 2018/5/16.
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String selectCentent(ModelMap map){
        List<TbContentUtil> list = contentService.selectCentent();
        String s = JsonUtils.objectToJson(list);
        map.put("ad1",s);
        return "index";
    }
}

