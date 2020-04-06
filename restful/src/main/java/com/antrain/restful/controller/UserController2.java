package com.antrain.restful.controller;

import com.antrain.restful.entity.User;
import com.antrain.restful.service.UserService;
import com.antrain.restful.util.PageResult;
import com.antrain.restful.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/adminAjax")
public class UserController2 {

    @Resource
    private UserService userService2;

    @GetMapping("/userPage")
    public String list(){
        return "admin/userManager";
    }

    @ResponseBody
    @GetMapping("/users")
    public PageResult userList(@RequestParam Map<String, Object> params){
        PageResult pageResult = userService2.queryUsers(new PageUtil(params));
        return pageResult;
    }

    public boolean valid(User user) {
        return user==null||user.getId()==null||user.getUsername() == null ||
                user.getPassword() == null || user.getBirth() == null || user.getSex() == null;

    }

    @ResponseBody
    @PostMapping("/user")
    public String add(@RequestBody User user){
       return String.valueOf(userService2.insert(user));
    }

    @ResponseBody
    @GetMapping("/user/{id}")
    public User toEdit(@PathVariable("id") Integer id){
        return userService2.queryById(id);
    }

    @ResponseBody
    @PutMapping("/user")
    public int update(@RequestBody User user){
        //System.out.println(user);
       return userService2.update(user);
    }

    @ResponseBody
    @DeleteMapping("/user/{id}")
    public int del(@PathVariable("id") Integer id){
        return userService2.del(id);
    }
}
