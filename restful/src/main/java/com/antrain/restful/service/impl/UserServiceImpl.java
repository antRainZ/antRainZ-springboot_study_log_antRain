package com.antrain.restful.service.impl;

import com.antrain.restful.entity.User;
import com.antrain.restful.mapper.UserMapper;
import com.antrain.restful.service.UserService;
import com.antrain.restful.util.PageResult;
import com.antrain.restful.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int del(int id) {
        return userMapper.del(id);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User queryById(int id) {
        return userMapper.queryById(id);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public PageResult queryUsers(PageUtil pageUtil) {
        List<User> users = userMapper.getBatch(pageUtil);
        int total = userMapper.getTotal();
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
