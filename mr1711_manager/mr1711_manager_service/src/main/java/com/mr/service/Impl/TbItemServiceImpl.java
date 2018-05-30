package com.mr.service.Impl;

import com.mr.mapper.TbItemMapper;
import com.mr.model.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by czl on 2018/5/22.
 */
@Service
@Transactional(readOnly = true)
public class TbItemServiceImpl implements TbItemService {
    @Autowired
    private TbItemMapper tbItemMapper;


    @Override
    public TbItem selectTbItemById(long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }


}

