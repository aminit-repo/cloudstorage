package com.udacity.jwdnd.course1.cloudstorage.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
public class FileController {
    private FilesService filesService;
    public final Logger logger = LoggerFactory.getLogger(FilesService.class);
    private CredentialService credentialService;
    private UsersService usersService;
    private NotesService notesService;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    public FileController(FilesService filesService,CredentialService credentialService, UsersService usersService, NotesService notesService) {
        this.filesService = filesService;
        this.credentialService= credentialService;
        this.usersService= usersService;
        this.notesService= notesService;
    }

    @PostMapping("uploadFiles")
    public String saveFiles(@RequestParam("fileUpload") MultipartFile multipartFile, Model model, Authentication authentication){
        String errorMessage=null;
        String successMessage=null;
        Users users=usersService.getUser(authentication.getName());
        try{
           Integer id= filesService.storeFile(multipartFile,users);
           successMessage= "file added successfully";
           System.out.println("file with name="+multipartFile.getOriginalFilename()+" return ="+id);
        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage= e.getMessage();
        }
        model.addAttribute("notes", new Notes(null, null, null,null));
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model, users);
        notesService.setModel(model, users, false);
        return "home";
    }

    @PostMapping("deleteFile")
    public  String deleteAFile(@RequestParam("fileId") String fileId, Model model, Authentication authentication){
       String successMessage= null;
       String errorMessage= null;
        Users users=usersService.getUser(authentication.getName());
        try {
            Integer fID= Integer.valueOf(fileId);
            if(filesService.deleteFile(fID) != null){
                successMessage="file deleted successfully";
            }else{
                errorMessage="file could not be deleted";
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage="error occurred while trying to delete file";
        }
        model.addAttribute("notes", new Notes(null, null, null,null));
        credentialService.setModal(model,successMessage, errorMessage, new Credentials(), users, false);
        filesService.setModal(model, users);
        notesService.setModel(model, users, false);
        return "home";
    }



    @GetMapping("/files/{Id:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String Id) {
       Integer fileId= Integer.valueOf(Id);
       Files files= filesService.getFile(fileId);
       return  filesService.loadAsEntity(files);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String  handleLargeFilesExceptions(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage","error uploading file, file size cannot exceed "+ maxFileSize);
        return "redirect:/home";
    }






}
