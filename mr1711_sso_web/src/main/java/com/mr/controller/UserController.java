package com.mr.controller;

import com.mr.CookieUtils;
import com.mr.DataResult;
import com.mr.JsonUtils;
import com.mr.model.TbUser;
import com.mr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by czl on 2018/5/17.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/user/check/{param}/{type}",method = RequestMethod.GET)
    public DataResult check(@PathVariable String param,@PathVariable Integer type){
        DataResult dataResult= userService.check(param,type);
        return dataResult;
    }
    @ResponseBody
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public DataResult register(TbUser user){
        DataResult dataResult= userService.register(user);
        return dataResult;
    }

    /*
    * 登录 并放入缓存中
    * */

    @ResponseBody
    @RequestMapping(value = "/user/login" , method = RequestMethod.POST)
    public DataResult login(String username , String password  ,
                            HttpServletResponse response,
                            HttpServletRequest request){
        DataResult dataResult = userService.login(username, password);
        //如果登录成功，
        if(dataResult.getStatus() == 200){
            //把token写入cookie
            CookieUtils.setCookie(request,response ,
                    "cookieToken" , dataResult.getData().toString());
        }
        return dataResult;
    }

    //http://localhost:8087/user/token/{token}
    @RequestMapping(value = "/user/token/{token}" , method = RequestMethod.GET)
    @ResponseBody
    public Object token(@PathVariable String token ,String callback){
        DataResult dataResult = userService.getInfoByToken(token);
        if(StringUtils.isNotBlank(callback)){
            return callback+"("+ JsonUtils.objectToJson(dataResult)+")";
        }
        return dataResult;
    }

    //http://localhost:8087/user/logout/{token}
    @RequestMapping(value = "/user/logout/{token}" , method = RequestMethod.GET)
    @ResponseBody
    public DataResult logout(@PathVariable String token){
        DataResult dataResult = userService.logout(token);
        return dataResult;
    }
}
