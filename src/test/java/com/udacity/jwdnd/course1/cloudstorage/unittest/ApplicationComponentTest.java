package com.udacity.jwdnd.course1.cloudstorage.unittest;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;

import java.security.SecureRandom;
import java.util.Base64;

public class ApplicationComponentTest {

    public void createCredential(){
        String enkey;
        SecureRandom random= new SecureRandom();
        byte[] bytes= new byte[16];
        random.nextBytes(bytes);
        enkey= Base64.getEncoder().encodeToString(bytes);

    }
}
