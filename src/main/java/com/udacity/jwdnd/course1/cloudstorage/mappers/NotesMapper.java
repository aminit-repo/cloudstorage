package com.udacity.jwdnd.course1.cloudstorage.mappers;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;
@Mapper
public interface NotesMapper {
    @Insert("INSERT INTO NOTES (noteTitle,noteDescription,userId) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public Integer insertNotes(Notes notes);

    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    public ArrayList<Notes> getAllNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteId=#{noteId}")
    public Notes getNotes(Integer noteId);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription} WHERE noteId=#{noteId}")
    public Integer updateNotes(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    public Integer deleteNote(Integer noteId);
}
