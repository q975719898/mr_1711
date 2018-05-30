package com.mr.service.Impl;

import com.mr.DataResult;
import com.mr.JsonUtils;
import com.mr.mapper.TbUserMapper;
import com.mr.model.TbUser;
import com.mr.model.TbUserExample;
import com.mr.service.UserService;
import com.mr.order.jedis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by czl on 2018/5/17.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
     private TbUserMapper userMapper;


    @Autowired
    private JedisClient jedisClient;
    @Override
    public DataResult check(String param, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (type==1){
            criteria.andUsernameEqualTo(param);
        }else if(type==2){
            criteria.andPhoneEqualTo(param);
        }else if (type==3){
            criteria.andEmailEqualTo(param);
        }else {
            return DataResult.build(404,"参数格式不正确");
        }
        List<TbUser> users = userMapper.selectByExample(example);
        if (users.size()==0){
           return DataResult.ok(true);
        }
        return DataResult.ok(false);
    }

    @Transactional
    @Override
    public DataResult register(TbUser user) {
       try {
           user.setCreated(new Date());
           user.setUpdated(new Date());
           String s = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
           user.setPassword(s);
           userMapper.insertSelective(user);
       }catch (Exception e){
           return DataResult.build(400,"注册失败,请检查您的数据类型");
       }
        return DataResult.ok();
    }
    public DataResult login(String userName, String password) {
        //判断用户密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(userName);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null && list.size() == 0) {//用户不存在
            return DataResult.build(400, "用户名或密码不正确！");
        }
        TbUser user = list.get(0);

        //密码需要MD5加密验证
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return DataResult.build(400, "用户名或密码不正确！");
        }
        //生成token,uuid
        String token = UUID.randomUUID().toString();

        //设置密码为null,因为获取用户信息时不需要获取到密码
        user.setPassword(null);
        //将数据存在redis中，key是token,值是用户信息
        jedisClient.set(token, JsonUtils.objectToJson(user));

        //设置key的有效时间。
        jedisClient.expire(token, 1800);
        //将token返回去
        return DataResult.ok(token);

    }
    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    public DataResult getInfoByToken(String token) {

        String userInfo = jedisClient.get(token);
        if(StringUtils.isBlank(userInfo)){
            return DataResult.build(400,"用户过期！");
        }

        //重新设置过期时间
        jedisClient.expire(token ,1800);
        TbUser user = JsonUtils.jsonToPojo(userInfo, TbUser.class);
        return DataResult.ok(user);
    }

    public DataResult logout(String token) {
        //删除信息
        jedisClient.del(token);
        return DataResult.ok();
    }
}
