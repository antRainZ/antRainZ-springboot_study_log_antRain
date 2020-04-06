package com.antrain.restful.mapper;

import com.antrain.restful.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper  {

    @Insert("insert into "+
            " user(id, username, password, sex, birth)"+
            "values (null, #{username,jdbcType=VARCHAR}, " +
            "#{password,jdbcType=VARCHAR}, #{sex,jdbcType=CHAR},#{birth, jdbcType=DATE} );")
    int insert(User user);

    @Delete("DELETE FROM user WHERE id=#{id};")
    int del(int id);

    @Update("UPDATE user SET username=#{username,jdbcType=VARCHAR}, password=#{password,jdbcType=VARCHAR} ,"+
            " sex= #{sex,jdbcType=CHAR},birth=#{birth,jdbcType=DATE} WHERE id=#{id,jdbcType=INTEGER};")
    int update(User user);

    @Select("SELECT * FROM user WHERE id=#{id};")
    User queryById(int id);

    @Select("SELECT * FROM user ;")
    List<User> queryAll();

    @Select(" <script>" +
            " SELECT * FROM user " +
            "<if test=\"offset!=null and limit!=null\">"+
            "limit #{offset},#{limit}"+
            "</if>"+
            " </script>")
    List<User> getBatch(Map params);

    @Select("select count(*) from user")
    int getTotal();
}
