package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UsersService {
    private UsersMapper usersMapper;
    private HashService hashService;

    public UsersService(UsersMapper usersMapper, HashService hashService) {
        this.usersMapper = usersMapper;
        this.hashService = hashService;
    }

    public Integer addUser(Users user) throws  Exception{
        //generate salt;
        SecureRandom random= new SecureRandom();
        byte[] salt= new byte[16];
        random.nextBytes(salt);
        String encodedSalt= Base64.getEncoder().encodeToString(salt);
        String harshPassword= hashService.getHashedValue(user.getPassword(),encodedSalt);
        return usersMapper.setUser(new Users(user.getUsername(),encodedSalt,harshPassword,user.getFirstname(),user.getLastname()));

    }


    public Users getUser(String username){
        return usersMapper.getUser(username);
    }

    public Boolean isUserExist(String username){
        if(getUser(username)!= null){
            return  true;
        }
        return false;
    }

}