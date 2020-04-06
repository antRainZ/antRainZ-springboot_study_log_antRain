package com.antrain.restful.service.impl;

import com.antrain.restful.entity.Admin;
import com.antrain.restful.mapper.AdminMapper;
import com.antrain.restful.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        return adminMapper.login(name, pwd);
    }
}
