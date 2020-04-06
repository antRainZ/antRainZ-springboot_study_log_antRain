package com.antrain.restful.controller;

import com.antrain.restful.entity.User;
import com.antrain.restful.service.UserService;
import com.antrain.restful.util.PageResult;
import com.antrain.restful.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Controller
public class ajaxController {

    @RequestMapping("/hello")
    public String hello(){
        return "admin/test";
    }

    @ResponseBody
    @RequestMapping("/hello/test")
    public User test(){
        return new User("antrain","123456","F",new Date());
    }

    @ResponseBody
    @RequestMapping("/hello/test2")
    public int test2(){
        return  1;
    }

}
