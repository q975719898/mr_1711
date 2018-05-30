package com.mr.controller;

import com.mr.CookieUtils;
import com.mr.JsonUtils;
import com.mr.model.TbItem;
import com.mr.service.Impl.TbItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by czl on 2018/5/23.
 */
@Controller
public class CartController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/cart/add/{itemId}/{num}")
    public String addCart(@PathVariable long itemId , @PathVariable Integer num,
                          HttpServletRequest request,HttpServletResponse response){
        //获取cookie中的list列表
        String s = CookieUtils.getCookieValue(request, "cartcookiename", true);
        boolean flag=false;
        List<TbItem>  items=null;
        if(StringUtils.isNotBlank(s)){
              items = JsonUtils.jsonToList(s, TbItem.class);//获取cookie中的list
            for (TbItem item: items){
                if(item.getId()==itemId){ //判断购物车中有无这个商品,如果有
                    item.setNum(item.getNum()+num);//则数量加N
                    flag=true;
                }
            }
            if(!flag){ //非 也就是如果不存在
                TbItem tbItem = tbItemService.selectTbItemById(itemId);//不存在则通过id查询 返回对应的商品信息
                tbItem.setNum(num);//将提交过来的数量附到对象里
                items.add(tbItem);//并将对象放到list集合里
            }
        }

        CookieUtils.setCookie(request,response,"cartcookiename",JsonUtils.objectToJson(items),300,true);
        return "cartSuccess";
    }
    @RequestMapping("/cart/cart")
    public String tocart(){
        return "cart";
    }

}