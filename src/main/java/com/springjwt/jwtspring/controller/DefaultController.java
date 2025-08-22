package com.springjwt.jwtspring.controller;

import com.springjwt.jwtspring.repo.UserRepo;
import com.springjwt.jwtspring.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class DefaultController {

    @Autowired
    JWTUtils utils;

    @GetMapping("/weather")
    public ModelAndView demoPage() {
        return new ModelAndView("index"); // Note: no ".html"
    }
}
