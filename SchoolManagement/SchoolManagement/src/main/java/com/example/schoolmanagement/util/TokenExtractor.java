package com.example.schoolmanagement.util;

import com.example.schoolmanagement.service.SchoolService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;

import java.util.Map;

@Setter
public class TokenExtractor {

    private String createBy;
    private String updatedBy;

    private static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public static void printTokenDetails(String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty.");
            return;
        }

        try {
            Claims claims = parseJwt(token);

            System.out.println("Token: " + token);
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());

//               if( entry.getKey().equalsIgnoreCase("role")){
//                   SchoolService.add(entry.getKey());
//               }

            }

        } catch (Exception e) {
            System.out.println("Error parsing token: " + e.getMessage());
        }

    }


    private static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Set the secret key used to sign the token
                .parseClaimsJws(token)     // Parse the JWT and extract claims
                .getBody();                // Return the claims (payload)
    }
}
//public static void add(String a){
//    System.out.println("service class : " +a);
//}