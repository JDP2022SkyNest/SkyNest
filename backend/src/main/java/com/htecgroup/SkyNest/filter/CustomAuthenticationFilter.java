package com.htecgroup.skynest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.SpringApplicationContext;
import com.htecgroup.skynest.model.dto.UserDto;
import com.htecgroup.skynest.model.request.UserLoginRequest;
import com.htecgroup.skynest.security.SecurityConstants;
import com.htecgroup.skynest.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
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
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            UserLoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>())
            );
        } catch (IOException e){

            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String userName = ((User) authentication.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(userName)
                        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                        .compact();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.findUserByEmail(userName);

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        response.addHeader("UserID", userDto.getId().toString());

        response.addHeader("UserID", userDto.getId().toString());

    }
}
