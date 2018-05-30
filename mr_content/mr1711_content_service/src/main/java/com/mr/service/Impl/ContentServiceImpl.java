package com.mr.service.Impl;

import com.mr.JsonUtils;
import com.mr.jedis.JedisClient;
import com.mr.mapper.TbContentMapper;
import com.mr.model.TbContent;
import com.mr.model.TbContentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czl on 2018/5/16.
 */
@Service
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;
    @Override
    public List <TbContentUtil> selectCentent() {
        /*查询缓存*/
        Boolean b = jedisClient.exists("content");
        if(b){
            String content = jedisClient.get("content");
            List<TbContentUtil> list1 = JsonUtils.jsonToList(content, TbContentUtil.class);
            return list1;
        }
        List<TbContent> list = tbContentMapper.selectByExample(null);
        TbContentUtil content= new TbContentUtil();
        List<TbContentUtil> list2= new ArrayList<>();
        for (int i=0;i<list.size();i++){
            content.setSrc(list.get(i).getPic());
            content.setSrcB(list.get(i).getPic2());
            content.setHeight("240");
            content.setHeightB("240");
            content.setWidth("670");
            content.setWidthB("550");
            content.setHref(list.get(i).getUrl());
            content.setAlt(list.get(i).getTitle());
            list2.add(content);
        }
        jedisClient.set("content", JsonUtils.objectToJson(list2));
        return list2;
    }
}
