package com.antrain.restful.service;

import com.antrain.restful.entity.User;
import com.antrain.restful.util.PageResult;
import com.antrain.restful.util.PageUtil;

import java.util.List;

public interface UserService {
    public int insert(User user);
    public int del(int id);
    public int update(User user);
    public User queryById(int id);
    public List<User> queryAll();
    PageResult queryUsers(PageUtil pageUtil);
}
