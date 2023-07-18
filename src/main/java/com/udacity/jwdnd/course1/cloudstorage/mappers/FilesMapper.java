package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FilesMapper {
    @Insert("INSERT INTO FILES (filename,contentType,fileSize, userId, fileData) VALUES (#{filename},#{contentType},#{fileSize}, #{userId}, #{fileData} )")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer insertFiles(Files files);

    @Select("SELECT * FROM FILES WHERE userId=#{userId}")
    public ArrayList<Files> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    public Files getFiles(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    public Files getFileWithFilename(String filename);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    public Integer deleteFile(Integer fileId);

}
