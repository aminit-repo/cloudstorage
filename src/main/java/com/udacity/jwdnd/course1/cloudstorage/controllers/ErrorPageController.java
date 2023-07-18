package com.udacity.jwdnd.course1.cloudstorage.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping("error")
    public String errorMessage(HttpServletRequest request, Model model){

       Object statusObject= request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
       if(statusObject != null){
        Integer statusCode= Integer.valueOf(statusObject.toString());
        if(statusCode == HttpStatus.NOT_FOUND.value()){
            model.addAttribute("errorCode", statusCode);
            model.addAttribute("errorMessage", "Ops! we could not find the requested page");
        }
        else{
            model.addAttribute("errorCode", statusCode);
            model.addAttribute("errorMessage", "Internal Server Error");
        }
       }
        return "error";
    }


}
