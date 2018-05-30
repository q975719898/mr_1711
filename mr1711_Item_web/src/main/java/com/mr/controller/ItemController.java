package com.mr.controller;

import com.mr.model.TbItem;
import com.mr.service.Impl.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**kitem
 * Created by czl on 2018/5/22.
 */
@Controller
public class ItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("item/{id}")
    public String getItem(@PathVariable long id, ModelMap map){
        TbItem tbItem = tbItemService.selectTbItemById(id);
        map.put("item",tbItem);
        return "item";
    }
}
