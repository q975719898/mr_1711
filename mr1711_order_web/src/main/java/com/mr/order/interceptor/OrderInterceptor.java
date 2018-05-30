package com.mr.order.interceptor;

import com.mr.CookieUtils;
import com.mr.DataResult;
import com.mr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by czl on 2018/5/24.
 */
public class OrderInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //查询token
        String token = CookieUtils.getCookieValue(request, "cookieToken", true);
        String url = request.getRequestURL().toString();//获得拦截的请求路径
        if(StringUtils.isBlank(token)){//如果token为空 也就是没登录
            response.sendRedirect("http://localhost:8087/page/login?url="+url);//重定向到登录页面,
            return false;
        }
        DataResult dataResult = userService.getInfoByToken(token);
        if(dataResult.getStatus()!=200){
            response.sendRedirect("http://localhost:8087/page/login?url="+url); //重定向到登录页面,
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
