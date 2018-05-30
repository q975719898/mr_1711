package com.mr.service;

import com.mr.DataResult;
import com.mr.model.TbUser;

/**
 * Created by czl on 2018/5/17.
 */
public interface UserService {
    DataResult check(String param, Integer type);

    DataResult register(TbUser user);

    DataResult login(String userName, String password);

    DataResult getInfoByToken(String token);

    DataResult logout(String token);
}
