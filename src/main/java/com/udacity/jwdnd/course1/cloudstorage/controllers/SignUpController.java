package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    private UsersService usersService;

    public SignUpController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute("user") Users user, Model model){
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("user") Users user, Model model){
        String errorMessage=null;
        String successMessage= null;
        //validate the object submitted is not null
        if(user!= null){
            try{
                //set the userId.
                user.setUserid(usersService.addUser(user));

                //create success message
                successMessage="You successfully signed up!";
            }catch (Exception e){
                errorMessage="User with "+user.getUsername()+" already exist";
            }
        }
        model.addAttribute("successMessage",successMessage);
        model.addAttribute("errorMessage",errorMessage);
        return "signup";
    }

}
