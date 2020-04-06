package com.antrain.restful.mapper;

import com.antrain.restful.entity.Admin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE name=#{name} AND pwd =#{pwd}")
    Admin login(String name, String pwd);
}
