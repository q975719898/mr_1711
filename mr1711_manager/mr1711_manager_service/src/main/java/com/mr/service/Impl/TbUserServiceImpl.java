package com.mr.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mr.mapper.TbUserMapper;
import com.mr.model.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by czl on 2018/5/15.
 */
@Service
@Transactional(readOnly = true)
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser select(long id) {

        return tbUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<TbUser> users = tbUserMapper.selectByExample(null);
        PageInfo<TbUser> pageInfo= new PageInfo<>(users);
        Map<String,Object> map =new HashMap<>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }

   /* public TbUser select(long id) {
        TbUser tbUser = tbUserMapper.selectByPrimaryKey(id);
        return  tbUser;
    }*/
}
