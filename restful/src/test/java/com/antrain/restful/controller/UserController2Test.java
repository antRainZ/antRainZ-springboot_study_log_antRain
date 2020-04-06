package com.antrain.restful.controller;

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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class UserController2Test {

    @Autowired
    private WebApplicationContext webApplicationContext2;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext2).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void userList() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("offset", "0");
        params.add("limit", "2");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/adminAjax/users").params(params))
                .andReturn();
        //System.out.println(mvcResult); //哈希码
        ModelAndView modelAndView=mvcResult.getModelAndView();//获取模型和视图
        //System.out.println(modelAndView); //null
        MockHttpServletResponse mockHttpServletResponse= mvcResult.getResponse();
        //System.out.println(mockHttpServletResponse);//哈希码
        String result =  mockHttpServletResponse.getContentAsString();//得到当前json
        System.out.println(result);
    }
}