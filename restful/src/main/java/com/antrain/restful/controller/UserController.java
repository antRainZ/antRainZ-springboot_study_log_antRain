package com.antrain.restful.controller;

import com.antrain.restful.entity.User;
import com.antrain.restful.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users",userService.queryAll());
        return "admin/list";
    }

    @GetMapping("/user")
    public  String toAdd(Model model){
        return "admin/add";
    }

    @PostMapping("/user")
    public String add(User user){
       // System.out.println("传入参数：==="+user);
        userService.insert(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/{id}")
    public String toEdit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user",userService.queryById(id));
        return "admin/add";
    }

    @PutMapping("/user")
    public String update(User user){
        //System.out.println("传入参数：==="+user);
        //System.out.println("修改前数据库内容：===="+userService.queryById(user.getId()));
        userService.update(user);
        //System.out.println("修改后数据库内容：===="+userService.queryById(user.getId()));
        return "redirect:/admin/users";
    }

    @DeleteMapping("/user/{id}")
    public String del(@PathVariable("id") Integer id){
        //System.out.println("修改前数据库内容：===="+userService.queryById(id));
        userService.del(id);
        //System.out.println("修改后数据库内容：===="+userService.queryById(id));
        return "redirect:/admin/users";
    }
}
