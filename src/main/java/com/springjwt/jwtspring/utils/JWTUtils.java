package com.springjwt.jwtspring.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    private static final long EXPIRY_DATE = 1000*60*60;

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

       return Jwts.builder()

               //payload
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_DATE))

               //signature
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
