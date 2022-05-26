package com.htecgroup.SkyNest.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/users/login")){
            filterChain.doFilter(request,response);
        }
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
              String token = authorizationHeader.substring("Bearer ".length());
              Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
              JWTVerifier verifier = JWT.require(algorithm).build();
              DecodedJWT decodedJWT = verifier.verify(token);
              String username = decodedJWT.getSubject();

              UsernamePasswordAuthenticationToken authenticationToken =
                  new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
              filterChain.doFilter(request, response);
        }
         else {
            filterChain.doFilter(request,response);
        }
    }
}
