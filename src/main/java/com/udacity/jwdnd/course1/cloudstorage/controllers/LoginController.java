package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    private UsersService usersService;

    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String fetchLogin(@ModelAttribute("user") Users user, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        if(authentication instanceof  AnonymousAuthenticationToken){
            return "login";
        }

        if (authentication != null) {
            if(authentication.isAuthenticated()){
                if(usersService.isUserExist(authentication.getName())){
                    //match the users credentials
                    return "redirect:/home";
                }
            }
        }

        return "login";
    }
}
