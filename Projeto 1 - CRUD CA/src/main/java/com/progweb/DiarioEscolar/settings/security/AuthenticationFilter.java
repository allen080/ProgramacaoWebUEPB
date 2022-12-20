package com.progweb.DiarioEscolar.settings.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.progweb.DiarioEscolar.domain.User;
import com.progweb.DiarioEscolar.domain.dtos.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;


    //cosntrutor
    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    //tentativa de autenticacao
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            //cria uma credencial (username e senha), e pega esses valores do pedido da request passada no http
            UserDTO user = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);

            //cria a autenticacao com username, senha e autenticacoes(array)
            UsernamePasswordAuthenticationToken authenticationToken =
                                      new UsernamePasswordAuthenticationToken(user.getNome(), user.getSenha(), new ArrayList<>());

            //verifica essas autenticacoes com a interface que autentifica esses dados 
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //caso der sucesso na autenticacao
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
    
            //authResult é o resultado da authenticcacao e ja pega o email autenticado
            String username = ((User) authResult.getPrincipal()).getUsername();
            String token = jwtUtil.generateToken(username);//cria o token com o email
            //retorno do response deve mandar se autorizo e o token
            response.setHeader("access-control-expose-headers", "Authorization");
            response.setHeader("Authorization", "Bearer "+ token);//deve retornar o bearer antes do token
            
        }

    //caso nao der sucesso na autenticacao
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());

    }

    private CharSequence json(){
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\" , "
                + "\"path\": \"/login\"}";
    }
    
    
}