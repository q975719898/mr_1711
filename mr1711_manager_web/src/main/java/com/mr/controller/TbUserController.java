package com.mr.controller;

import com.mr.model.TbUser;
import com.mr.service.Impl.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by czl on 2018/5/15.
 */
@Controller("tbUserController")
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;

    @ResponseBody
    @RequestMapping("/select/{id}")
    public TbUser select(@PathVariable("id") long id){
        TbUser tbUser =  tbUserService.select(id);
        return tbUser;
    }
    @RequestMapping("/tomain/")
    public String tomain(){
        return "main";
    }
    @RequestMapping("/tolist/")
    public String tolist(){
        return "list";
    }
    @ResponseBody
    @RequestMapping("/TbUser/select/")
    public Map<String,Object> select(Integer page,Integer rows){
        Map<String,Object>map = tbUserService.selectAll(page,rows);
        return map;
    }
}
