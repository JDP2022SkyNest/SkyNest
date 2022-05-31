package com.htecgroup.skynest.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import com.htecgroup.skynest.model.request.UserLoginRequest;
import com.htecgroup.skynest.security.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Log4j2
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginRequest credentials = new UserLoginRequest();
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
            log.info("Here, with help of object mapper we map the value of the request to UserLoginRequest class");

        } catch (IOException e) {
            log.error(e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getEmail());
        if (!credentials.getEmail().equals(userDetails.getUsername())){
            throw new UserException(UserExceptionType.INVALID_EMAIL_OR_PASSWORD);
        }

        if (!passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
            throw new UserException(UserExceptionType.PASSWORDS_DOES_NOT_MATCH);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("We get in this method, only if we return from attemptAuthenticate method successfully");
        String userName = ((User) authentication.getPrincipal()).getUsername();

        String token = JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SecurityConstants.TOKEN_SECRET));

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        log.info("We create the token for the user, with that specific username, and we add the token in the header with key Authentication");

    }
}