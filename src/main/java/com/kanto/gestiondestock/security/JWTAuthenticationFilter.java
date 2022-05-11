package com.kanto.gestiondestock.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanto.gestiondestock.entity.Utilisateur;
import com.kanto.gestiondestock.entity.auth.ExtendedUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Utilisateur utilisateur = null;

        try {
            utilisateur=new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class) ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("*************************");
        System.out.println("Login: " + utilisateur.getMail());
        System.out.println("Password: " + utilisateur.getPassword());

        //donnner a spring security le username et le password
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateur.getMail(), utilisateur.getPassword())
        ) ;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //generer le token
        ExtendedUser springUser = (ExtendedUser) authResult.getPrincipal();
        String jwt = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET)
                .claim("roles", springUser.getAuthorities())
                .claim("idEntreprise", springUser.getIdEntreprise())
                .compact();


        //mettre le token dans response et le placer dans le header
        response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX+jwt);


        System.out.println("******LE TOKEN********");
        System.out.println(jwt);

    }
}
