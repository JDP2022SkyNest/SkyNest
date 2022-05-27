package com.htecgroup.SkyNest.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.htecgroup.SkyNest.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
    public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       String header = request.getHeader(SecurityConstants.HEADER_STRING);

       if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
           filterChain.doFilter(request,response);
       }

       UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
       SecurityContextHolder.getContext().setAuthentication(authenticationToken);
       filterChain.doFilter(request,response);


//
//        if(request.getServletPath().equals("/users/login")){
//            filterChain.doFilter(request,response);
//        }
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//              String token = authorizationHeader.substring("Bearer ".length());
//              Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//              JWTVerifier verifier = JWT.require(algorithm).build();
//              DecodedJWT decodedJWT = verifier.verify(token);
//              String username = decodedJWT.getSubject();
//
//              UsernamePasswordAuthenticationToken authenticationToken =
//                  new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//              filterChain.doFilter(request, response);
//        }
//         else {
//            filterChain.doFilter(request,response);
//        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if(token != null){
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if(user != null){
                return new UsernamePasswordAuthenticationToken(user,null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
