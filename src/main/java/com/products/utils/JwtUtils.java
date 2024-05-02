package com.products.utils;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String key;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;


    public String createToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(this.key);

        String usuario =authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
                .stream()
                .map( grantedAuthority -> grantedAuthority.getAuthority())
                .peek(gr-> System.out.println("peek create token " + gr))
                .collect(Collectors.joining(","));
        System.out.println("authorities create token " + authorities);

        //firmar el token
        String token = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(usuario)
                .withClaim("authorities",authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis())) // apartir de que momento el token sera valido
                .sign(algorithm);

        System.out.println("token utils create toke" +token);

        return token;
    }

    public DecodedJWT validarToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);


            System.out.println("decodedJWT validate token" + decodedJWT);
          return decodedJWT;
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("el token ingresado es invalido, no autorizado");
        }
    }


    public String extractUserName(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT , String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String,Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }



}
