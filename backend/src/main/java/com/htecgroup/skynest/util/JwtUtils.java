package com.htecgroup.skynest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Log4j2
@Component
public class JwtUtils {

  public static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
  public static final String TOKEN_PREFIX = "Bearer ";

  public static long ACCESS_TOKEN_EXPIRATION_MS;
  private static Algorithm ALGORITHM;

  public static String generate(
      String userName, long msUntilExpiration, String claimName, List<String> claims) {
    return JWT.create()
        .withSubject(userName)
        .withExpiresAt(new Date(System.currentTimeMillis() + msUntilExpiration))
        .withClaim(claimName, claims)
        .sign(ALGORITHM);
  }

  public static UsernamePasswordAuthenticationToken getFrom(String token) {
    try {
      DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);

      String username = decodedJWT.getSubject();
      String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
      Collection<SimpleGrantedAuthority> authorities =
          stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

      return new UsernamePasswordAuthenticationToken(username, null, authorities);
    } catch (JWTVerificationException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new UserException(UserExceptionType.INVALID_TOKEN);
    } catch (IllegalArgumentException e) {
      log.error("JWT algorithm is null: {}", e.getMessage());
      throw new UserException(UserExceptionType.JWT_ALGORITHM_IS_NULL);
    }
  }

  public static long validFor(String token) {
    try {
      DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);
      Date expiresAt = decodedJWT.getExpiresAt();
      Date now = new Date(System.currentTimeMillis());
      return (expiresAt.getTime() - now.getTime()) / 1000;
    } catch (JWTVerificationException e) {
      return 0;
    } catch (IllegalArgumentException e) {
      log.error("JWT algorithm is null: {}", e.getMessage());
      throw new UserException(UserExceptionType.JWT_ALGORITHM_IS_NULL);
    }
  }

  @Value("${jwt.access-expiration-ms}")
  public void setNameStatic(long expirationMs) {
    ACCESS_TOKEN_EXPIRATION_MS = expirationMs;
  }

  @Value("${jwt.secret}")
  public void setAlgorithm(String secret) {
    ALGORITHM = Algorithm.HMAC512(secret);
  }
}
