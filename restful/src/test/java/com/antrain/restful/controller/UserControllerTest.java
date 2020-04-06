package com.antrain.restful.controller;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

//@Transactional 执行完后对数据库进行回滚

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/users"))
                .andReturn();
        ModelAndView modelAndView=mvcResult.getModelAndView();//获取模型和试图
        System.out.println(modelAndView.getModel());
        //System.out.println(modelAndView.getView());
        MockHttpServletResponse response = mvcResult.getResponse();
        //拿到请求返回码
        int status = response.getStatus();
        //拿到结果
        String contentAsString = response.getContentAsString();
        System.out.println("status ===="+status);
        //System.out.println("result ====" + contentAsString); html内容
    }

    @Test
    void add() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "super");
        params.add("password", "123456");
        params.add("sex","M");
        params.add("birth","2020-02-02");
        MvcResult msg = mockMvc.perform(MockMvcRequestBuilders.post("/admin/user")
                .params(params)).andReturn();
        Assert.assertEquals(200,msg.getResponse().getStatus());

    }

    @Test
    void update() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id","1");
        params.add("username", "super");
        params.add("password", "123456");
        params.add("sex","M");
        params.add("birth","2020-02-02");
        MvcResult msg = mockMvc.perform(MockMvcRequestBuilders.put("/admin/user")
                .params(params)).andReturn();
        Assert.assertEquals(200,msg.getResponse().getStatus());
    }

    @Test
    void del() throws Exception {
        MvcResult msg = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/user/1")).andReturn();
        Assert.assertEquals(200,msg.getResponse().getStatus());
    }
}