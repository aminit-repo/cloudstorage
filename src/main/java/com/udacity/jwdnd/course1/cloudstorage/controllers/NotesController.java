package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;

@Controller
public class NotesController {
    private UsersService usersService;
    private NotesService notesService;
    private CredentialService credentialService;
    private FilesService filesService;

    public final Logger logger = LoggerFactory.getLogger(NotesController.class);

    public NotesController(UsersService usersService, NotesService notesService, CredentialService credentialService, FilesService filesService) {
        this.usersService = usersService;
        this.notesService = notesService;
        this.credentialService = credentialService;
        this.filesService = filesService;
    }

    @PostMapping("saveNote")
    public String saveNote(@ModelAttribute("notes") Notes notes, Model model, Authentication authentication){
        Users users= usersService.getUser(authentication.getName());
        String errorMessage=null;
        String successMessage=null;
        try {
            notes.setUserid(users.getUserid());
            if(notesService.insertNotes(notes)!= null){
                successMessage="note added successfully";
            }else{
                errorMessage="note could not be added";
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage="an error occurred while trying to insert note";
        }
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model, users);
        notesService.setModel(model, users, false);
        return "home";
    }

    @GetMapping("editNote")
    public String editNote(@RequestParam("noteId") String noteId, Model model, Authentication authentication){
        Users users= usersService.getUser(authentication.getName());
        String errorMessage=null;
        String successMessage=null;
        try {
           Notes notes =notesService.getNotes(Integer.valueOf(noteId));
           if(notes == null){
               model.addAttribute("errorMessage","note not found");
               notesService.setModel(model, users, false);
           }else{
               model.addAttribute("notes", notes);
               notesService.setModel(model, users, true);
           }

        }catch (Exception e){
            model.addAttribute("errorMessage", "Error occurred while fetching notes");
            model.addAttribute("notes", new Notes(null, null, null,null));
            logger.error(e.getMessage());
        }
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model, users);
        return "home";
    }

    @GetMapping("deleteNote")
    public String deleteNote(@RequestParam("noteId") String noteId, Model model, Authentication authentication){
        Users users= usersService.getUser(authentication.getName());
        String errorMessage=null;
        String successMessage=null;
        try {
            if(notesService.deleteNote(Integer.valueOf(noteId))!=null){
                successMessage="note was deleted successfully";
            }else{
                errorMessage="note could note be deleted";
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage="an internal server error occurred while deleting note";
        }
        model.addAttribute("notes", new Notes(null, null, null,null));
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model, users);
        notesService.setModel(model, users, false);
        return "home";
    }

    @PostMapping("updateNote")
    public String updateNote(@ModelAttribute("notes") Notes notes, Model model, Authentication authentication){
        Users users= usersService.getUser(authentication.getName());
        String errorMessage=null;
        String successMessage=null;
        System.out.println(notes.getNoteDescription()+"  ,"+ notes.getNoteTitle()+" , "+notes.getNoteId());

        try{

            if(notesService.updateNotes(notes)!= null){
                successMessage="note was updated successfully";
            }else {
                errorMessage="an error occurred while updating note";
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage="an internal server error occurred while updating note";
        }
        notesService.setModel(model, users, false);
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model, users);
        return "home";
    }



}
