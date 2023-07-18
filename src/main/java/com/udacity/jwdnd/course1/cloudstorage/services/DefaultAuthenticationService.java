package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class DefaultAuthenticationService implements AuthenticationProvider{
    private HashService hashService;
    private UsersService usersService;

    public DefaultAuthenticationService(HashService hashService, UsersService usersService) {
        this.hashService = hashService;
        this.usersService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //get users credentials
        String username= authentication.getName();
        String password= authentication.getCredentials().toString();
        //get the users credentials
        Users user = usersService.getUser(username);
        if(user!= null){
            String hashPassword=hashService.getHashedValue(password,user.getSalt());
            if(user.getPassword().equals(hashPassword)){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }else{
                throw new BadCredentialsException("incorrect password");
            }
        }
           throw new UsernameNotFoundException("User does not exist");
    }


    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
