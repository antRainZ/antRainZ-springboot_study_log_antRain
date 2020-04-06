package com.antrain.restful;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.antrain.restful.mapper")
@SpringBootApplication
public class RestfulApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RestfulApplication.class);
        application.setBannerMode(Banner.Mode.OFF);//关闭
        application.run(args);
    }

}
