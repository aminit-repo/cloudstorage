package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.script.ScriptEngine;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {
    private EncryptionService encryptionService;

    private CredentialsMapper credentialsMapper;
    private Logger logger = LoggerFactory.getLogger(CredentialService.class);


    public CredentialService(EncryptionService encryptionService, CredentialsMapper credentialsMapper) {
        this.encryptionService = encryptionService;
        this.credentialsMapper = credentialsMapper;

    }

    public Integer addCredentials(Credentials credentials) throws Exception{
        String encryptedPassword;
        String enkey;
        SecureRandom random= new SecureRandom();
        byte[] bytes= new byte[16];
        random.nextBytes(bytes);
        enkey=Base64.getEncoder().encodeToString(bytes);
        String password="";
        //encrypt the credential password if available
        if(credentials.getPassword()!=null && credentials.getPassword() != ""){
            //encrypt password
            password=  encryptionService.encryptValue(credentials.getPassword(), enkey);
        }
         return credentialsMapper.setCredentials(new Credentials(null,credentials.getUrl(),credentials.getUsername(),enkey, password,credentials.getUserId()));

    }

    public Credentials getCredentials(Integer credentialId) throws  Exception{
        return  credentialsMapper.getCredentialsById(credentialId);
    }

    public ArrayList<Credentials> getAllCredentials(Integer userId) throws Exception{
        return  credentialsMapper.getAllCredentials(userId);
    }

    public Integer updateCredential(Credentials credentials) throws Exception{
        //harsh the password
        String encryptedPassword;
        String enkey;
        SecureRandom random= new SecureRandom();
        byte[] bytes= new byte[16];
        random.nextBytes(bytes);
        enkey=Base64.getEncoder().encodeToString(bytes);
        String password="";
        //encrypt the credential password if available
        if(credentials.getPassword()!=null && credentials.getPassword() != ""){
            //encrypt password
            password=  encryptionService.encryptValue(credentials.getPassword(), enkey);
        }
        return  credentialsMapper.updateCredential(new Credentials(credentials.getCredentialId(), credentials.getUrl(), credentials.getUsername(),enkey,password,null));
    }

    public Integer deleteCredential(Integer credentialId){
        return credentialsMapper.deleteCredential(credentialId);
    }

    public void setModal(Model model, String successMessage, String errorMessage,Credentials credentials, Users username, Boolean edit){
        ArrayList<Credentials> credentialList= new ArrayList<>();
        //get all the list of saved credentials
        try {
            Users user = username;
            //add userId to the credential object
            credentialList = getAllCredentials(user.getUserid());
        }catch (Exception e){
            logger.error(e.getMessage());
            errorMessage="an error occurred while performing operation";
        }
        model.addAttribute("isEditCredentials",edit);
        model.addAttribute("credentials", credentials);
        model.addAttribute("credentialList", credentialList);
        model.addAttribute("errorMessage",errorMessage);
        model.addAttribute("successMessage", successMessage);

    }


}
