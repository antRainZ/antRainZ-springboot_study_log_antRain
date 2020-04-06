package com.antrain.restful.controller;

import com.antrain.restful.entity.Admin;
import com.antrain.restful.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            map.put("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        Admin adminUser = adminService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getName());
            session.setAttribute("loginUserId", adminUser.getAid());
            //session过期时间设置为7200秒 即两小时
            session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            map.put("errorMsg", "登陆失败");
            return "admin/login";
        }
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "admin/index";
    }

}
