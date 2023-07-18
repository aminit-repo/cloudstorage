package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;

@Controller
public class pageController {
    private CredentialService credentialService;
    private UsersService usersService;
    private EncryptionService encryptionService;

    private FilesService filesService;
    private NotesService notesService;
    private Logger logger = LoggerFactory.getLogger(HashService.class);

    public pageController(CredentialService credentialService, UsersService usersService, EncryptionService encryptionService, FilesService filesService, NotesService notesService) {
        this.credentialService = credentialService;
        this.usersService = usersService;
        this.encryptionService= encryptionService;
        this.filesService= filesService;
        this.notesService = notesService;
    }

    @GetMapping("/home")
    public String getHome(@ModelAttribute("credentials") Credentials credentials, Model model, Authentication authentication){
        model.addAttribute("isEditCredentials",false);
        String successMessage=null;
        String errorMessage= null;
        Users users=usersService.getUser(authentication.getName());
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model,users);
        notesService.setModel(model, users, false);
        model.addAttribute("notes", new Notes(null,null, null,null));
        return "home";
    }


    @PostMapping("/credentials")
    public String postCredentials(@ModelAttribute("credentials") Credentials credentials, Model model, Authentication authentication){
        String errorMessage=null;
        String successMessage=null;
        Integer credentialId;

        try{
           Users user = usersService.getUser(authentication.getName());
           //add userId to the credential object
            credentials.setUserId(user.getUserid());
            //try to save the credential object
            try{
                credentialId=credentialService.addCredentials(credentials);
                successMessage="credentials added successfully";

            }catch (Exception e){
                errorMessage="an error occurred while performing operation";
                logger.error(e.getMessage());
            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        Users users= usersService.getUser(authentication.getName());
        //add a fragment on this page
        credentialService.setModal(model,successMessage, errorMessage,credentials,users, false);
        filesService.setModal(model,users);
        notesService.setModel(model, users, false);
        model.addAttribute("notes", new Notes(null,null, null,null));
        return "home";
    }


    @GetMapping("editCredentials")
    public String editCredentials(@ModelAttribute("credentials") Credentials credentials, Model model, Authentication authentication){
        Integer credentialId=credentials.getCredentialId();
        String errorMessage=null;
        String successMessage=null;
            try {
                //get the credential
                credentials= credentialService.getCredentials(credentialId);
                if(credentials!= null){
                    //decrypt password
                    String decryptedPassword= encryptionService.decryptValue(credentials.getPassword(),credentials.getEnKey());
                    credentials.setPassword(decryptedPassword);
                }else{
                    //set all to be null
                    credentials= new Credentials(null, null, null, null, null, null);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }

        Users users= usersService.getUser(authentication.getName());
        credentialService.setModal(model,successMessage, errorMessage,credentials, users, true);
        filesService.setModal(model,users);
        notesService.setModel(model, users, false);
        model.addAttribute("notes", new Notes(null,null, null,null));
            return "home";
    }
    @PostMapping("updateCredential")
    public  String updateCredentials(@ModelAttribute("credentials") Credentials credentials, Model model,  Authentication authentication){
        String errorMessage=null;
        String successMessage=null;
        try{
            Integer credentialId= credentialService.updateCredential(credentials);
            successMessage= "credentials updated successfully";
        }catch (Exception e){
            errorMessage="error occurred while updating credentials";
        }
        Users users= usersService.getUser(authentication.getName());
        credentialService.setModal(model,successMessage, errorMessage,credentials, users, false);
        filesService.setModal(model,users);
        notesService.setModel(model, users, false);
        model.addAttribute("notes", new Notes(null,null, null,null));
        return "home";
    }


    @PostMapping("/deleteCredentials")
    public  String deleteCredentials(@ModelAttribute("credentials") Credentials credentials, Model model,  Authentication authentication){
        String errorMessage=null;
        String successMessage=null;
        try {
            credentialService.deleteCredential(credentials.getCredentialId());
            successMessage="credential deleted";
        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage= "error occurred while performing operation";
        }
        Users users= usersService.getUser(authentication.getName());
        credentialService.setModal(model,successMessage, errorMessage,credentials, users, false);
        filesService.setModal(model,users);
        notesService.setModel(model, users, false);
        model.addAttribute("notes", new Notes(null,null, null,null));
        return "home";
    }



}
