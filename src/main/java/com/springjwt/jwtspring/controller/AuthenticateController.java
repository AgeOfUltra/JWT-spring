package com.springjwt.jwtspring.controller;

import com.springjwt.jwtspring.pojo.AuthenticateRequest;
import com.springjwt.jwtspring.utils.JWTUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticateController {

    // in securityConfig i have created the bean for authentication manager

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtils  jwtUtils;

    @PostMapping("/authenticate") // this is an open request.
    public ResponseEntity<String> authenticateUser(@RequestBody AuthenticateRequest request, HttpServletResponse response){

        String token;
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            //TODO save the jwt token in cookies.
            token = jwtUtils.generateToken(request.getUsername());
            Cookie cookie = new Cookie("JWT_TOKEN",token);
            cookie.setPath("/app");
            cookie.setHttpOnly(false);
            cookie.setMaxAge(1800);
            response.addCookie(cookie); 
            return ResponseEntity.ok(token);
        }catch (Exception e){
//            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
