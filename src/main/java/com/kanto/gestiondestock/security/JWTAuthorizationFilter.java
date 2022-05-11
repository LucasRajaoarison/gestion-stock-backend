package com.kanto.gestiondestock.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With,Content-Type,"
                + "Access-Control-Request-Method, "
                + "Access-Control-Request-Headers,"
                + "authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, "
                + "Access-Control-Allow-Credentials, Authorization");

        String jwt = request.getHeader(SecurityConstant.HEADER_STRING);
        System.out.println(jwt);

        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        } else {


            if (jwt == null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX, "")) //supprimer le Bearer
                    .getBody(); //recuperer le contenue du token
            String username = claims.getSubject();

            Integer idEntreprise = (Integer) claims.get("idEntreprise");

            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

            Collection<GrantedAuthority> authorities = new ArrayList<>() ;
            roles.forEach(r->{
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
            });
            UsernamePasswordAuthenticationToken authenticatedUser =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            System.out.println("Le ID entreprise est : " + idEntreprise);

            //on donne le username et son role a spring et il le charge,
            //comme ca il connait le username et le role (pour savoir les routes autoriser ou non...)
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            filterChain.doFilter(request, response);

        }

    }


}
