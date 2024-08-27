package io.github.dodo939.mapper;

import io.github.dodo939.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Insert("insert into user(username, password, create_time, update_time) values(#{username}, #{md5Password}, now(), now())")
    void addUser(String username, String md5Password);

    @Update("update user set nickname = #{nickname}, email = #{email}, update_time = now() where id = #{id}")
    void updateUser(User user);
}
