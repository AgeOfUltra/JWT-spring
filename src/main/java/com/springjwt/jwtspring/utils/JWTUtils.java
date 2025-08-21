package com.springjwt.jwtspring.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtils {

    private static final long EXPIRY_DATE = 1000*60*30;
    private  String jwtToken= "";

    private boolean isAuthenticated= false;

    private final String SECRET = "Very-secret-key-unlock-0r-hack-the-application-f0r-tim3-taken@90908762312";

    private final SecretKey key  = Keys.hmacShaKeyFor(SECRET.getBytes());

    public  String generateToken(String username){
/*
* JWT token structure
* part 1: header contains the which type of algorithm // added default same as signature.
*
* part 2: payload contains subject,initiated date , expiry date
*
* part 3: signature contains what is the secret key and hash algorithm.
* */

       String token= Jwts.builder()

               //payload
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_DATE))

               //signature
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

       jwtToken += token;
       isAuthenticated=true;
       return token;
    }

    public String getUserNameFromToken(String token){
        return extractorMethod(token).getSubject();
    }

    private Claims extractorMethod(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String username, String username1, String token) {
        return username.equals(username1) && !isTokenValid(token) ;
    }

    private boolean isTokenValid(String token) {
        return extractorMethod(token).getExpiration().before(new Date());
    }

    public String getSecretToken(){

        return isAuthenticated ?  jwtToken : "please authenticate again";
    }
}
