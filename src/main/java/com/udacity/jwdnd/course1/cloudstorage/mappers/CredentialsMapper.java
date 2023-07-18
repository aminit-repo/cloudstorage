package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.apache.ibatis.annotations.*;

import java.awt.image.CropImageFilter;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, enKey, password, userId) VALUES(#{url}, #{username}, #{enKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer setCredentials(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    public Credentials getCredentialsById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    public ArrayList<Credentials> getAllCredentials(Integer userId);

    @Update("UPDATE CREDENTIALS SET password=#{password}, username=#{username}, url=#{url}, enKey=#{enKey} WHERE credentialId=#{credentialId}")
    public Integer updateCredential(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    public Integer  deleteCredential(Integer credentialId);


}
