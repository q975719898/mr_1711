package com.mr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by czl on 2018/5/21.
 */
@Controller
public class PageController {


    //登录页面
    @RequestMapping("/page/login")
    public String tologin(String url, ModelMap map){
        map.put("redirect",url);
        return "login";
    }
    //注册页面
    @RequestMapping("/page/register")
    public String toregister(){
        return "register";
    }
}
