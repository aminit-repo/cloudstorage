package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

    @Insert("INSERT INTO USERS (username,password, firstname, lastname, salt) VALUES(#{username}, #{password}, #{firstname}, #{lastname}, #{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public Integer setUser(Users user);

    @Select("SELECT * FROM USERS WHERE username=#{username}")
    public Users getUser(String username);
}
