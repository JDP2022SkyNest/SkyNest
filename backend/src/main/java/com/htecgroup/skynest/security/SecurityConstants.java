package com.htecgroup.skynest.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel. PRIVATE)
public class SecurityConstants {

    public static final long EXPIRATION_TIME = 180000; // 30 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/register";

    public static final String LOG_IN_URL = "/users/login";
    public static final String TOKEN_SECRET = "jfgi95jgnfi4";


}
