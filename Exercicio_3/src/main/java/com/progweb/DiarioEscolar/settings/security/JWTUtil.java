package com.progweb.DiarioEscolar.settings.security;

import org.springframework.stereotype.Component;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JWTUtil {
    
    //pegar os valores salvos la no aplication.properties
    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    //criar o totem com nome+data+segredo e gera o token  = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImF5bGFuQGJvc2Nhcmluby5
    public String generateToken(String username){
        return Jwts.builder()
                    .setSubject(username)//gerar o token a apartir da informacao do username
                    .setExpiration(new Date(System.currentTimeMillis()+expiration))//tempo de expiracao é a hora atual mais 3 minutos
                    .signWith(SignatureAlgorithm.HS256, secret.getBytes())//tipo do algoritmo para fazer o hash e o secredo
                    .compact();
    }


  //fazer a verificacao do token criado com o token recebido
    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if(username != null && expirationDate != null && now.before(expirationDate)){
                return true;
            }   
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    //A partir do token tirar o username do claims
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            return claims.getSubject();
        }
        return null;
    }

}