package com.htecgroup.skynest.security;

public class SecurityConstants {

    private SecurityConstants(){
        throw new IllegalStateException("Utility class");
    }
    public static final long EXPIRATION_TIME = 180000; // 30 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/users/register";
    public static final String TOKEN_SECRET = "jfgi95jgnfi4";


}
