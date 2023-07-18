package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
@Service
public class NotesService {
    NotesMapper notesMapper;
    public final Logger logger = LoggerFactory.getLogger(NotesService.class);

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public ArrayList<Notes> getAllNotes(Integer userId) throws Exception{
        return notesMapper.getAllNotes(userId);
    }

    public Notes getNotes(Integer noteId) throws  Exception{
        return notesMapper.getNotes(noteId);
    }

    public Integer insertNotes(Notes notes) throws Exception{
        return notesMapper.insertNotes(new Notes(null,notes.getNoteTitle(), notes.getNoteDescription(), notes.getUserId()));
    }

    public Integer updateNotes(Notes notes) throws Exception{
        return notesMapper.updateNotes(notes);
    }

    public void setModel(Model model, Users users, Boolean edit){
        model.addAttribute("isEditNotes",edit);
        try {
            ArrayList<Notes> noteList= getAllNotes(users.getUserid());
            model.addAttribute("noteList", noteList);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public Integer deleteNote(Integer noteId) throws Exception{
        return notesMapper.deleteNote(noteId);
    }

}
