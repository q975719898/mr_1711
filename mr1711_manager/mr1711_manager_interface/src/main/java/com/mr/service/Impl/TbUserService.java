package com.mr.service.Impl;

import com.mr.model.TbUser;

import java.util.Map;

/**
 * Created by czl on 2018/5/15.
 */
public interface TbUserService {
    TbUser select(long id);

    Map<String,Object> selectAll(Integer page, Integer rows);
}
