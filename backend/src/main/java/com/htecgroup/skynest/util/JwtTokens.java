package com.htecgroup.skynest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokens {
  public static String generateToken(String userName, Date expiration, Algorithm algorithm) {
    return JWT.create().withSubject(userName).withExpiresAt(expiration).sign(algorithm);
  }
}
