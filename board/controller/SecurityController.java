package com.flowering.project.board.controller;


import com.flowering.project.board.security.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;
    
    @GetMapping("/system/login")
    public void login(){};

    @GetMapping("/system/accessDenied")
    public void accessDenied(){}

    @GetMapping("/system/logout")
    public void logout(){}

    @GetMapping("/system/adminPage")
    public void adminPage(){}
}
