package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class FilesService {
    private FilesMapper filesMapper;

    public FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public Integer storeFile(MultipartFile file, Users users) throws  Exception{
        //check if file is not empty
        if(file.isEmpty()){
            throw new FileUploadException("Failed to store empty file " + file.getOriginalFilename());
        }else{
            //create a new Files object from the multipart file
            if(isFileExist(file)){
                throw new FileAlreadyExistsException("Failed to store existing file "+ file.getOriginalFilename());
            }else{
                Files newFiles= new Files(null, file.getOriginalFilename(),file.getContentType(),Long.toString(file.getSize()), users.getUserid(), file.getBytes());
                return filesMapper.insertFiles(newFiles);
            }
        }
    }

    public Files getFile(Integer fileId){
        return  filesMapper.getFiles(fileId);
    }


    public ResponseEntity<Resource> loadAsEntity(Files file){
        Resource resource= new ByteArrayResource(file.getFileData());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(resource);
    }



    public Boolean isFileExist(MultipartFile file) throws Exception{
        //get all files from database
        Files files=filesMapper.getFileWithFilename(file.getOriginalFilename());
        if(files == null){
            return false;
        }
       return true;
    }

    public Integer deleteFile(Integer fileId) throws Exception{
        return filesMapper.deleteFile(fileId);
    }



    public ArrayList<Files> getAllFiles(Integer userId){
        return filesMapper.getAllFiles(userId);
    }

    public void setModal(Model model, Users users){
        ArrayList<Files> fileList=  getAllFiles(users.getUserid());
        model.addAttribute("fileList",fileList);
    }


}
