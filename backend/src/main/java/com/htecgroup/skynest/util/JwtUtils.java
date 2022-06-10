package com.htecgroup.skynest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.htecgroup.skynest.exception.UserException;
import com.htecgroup.skynest.exception.UserExceptionType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
  public static long EMAIL_TOKEN_EXPIRATION_MS;
  private static Algorithm ALGORITHM;

  private static final String EMAIL_TOKEN_CLAIM = "Email Token";
  public static final String EMAIL_VERIFICATION_PURPOSE = "verification";
  public static final String PASSWORD_RESET_PURPOSE = "password reset";

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

  public static String generateEmailToken(String email, String purpose) {
    return JWT.create()
        .withSubject(email)
        .withExpiresAt(new Date(System.currentTimeMillis() + EMAIL_TOKEN_EXPIRATION_MS))
        .withClaim(EMAIL_TOKEN_CLAIM, purpose)
        .sign(ALGORITHM);
  }

  public static void validateEmailToken(String token, String purpose) {
    try {
      Verification verification = JWT.require(ALGORITHM);
      Date expiresAt = verification.build().verify(token).getExpiresAt();
      JWTVerifier verifier =
          verification
              .acceptExpiresAt(expiresAt.getTime())
              .withClaim(EMAIL_TOKEN_CLAIM, purpose)
              .build();
      verifier.verify(token);
    } catch (JWTVerificationException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new UserException(UserExceptionType.INVALID_TOKEN);
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new UserException("Access token is missing", HttpStatus.BAD_REQUEST);
    }
  }

  public static String getEmailFromJwtEmailToken(String token) {
    return JWT.require(ALGORITHM).build().verify(token).getSubject();
  }

  @Value("${jwt.access-expiration-ms}")
  private void setAccessTokenExpirationMs(long expirationMs) {
    ACCESS_TOKEN_EXPIRATION_MS = expirationMs;
  }

  @Value("${jwt.email-expiration-ms}")
  private void setEmailTokenExpirationMs(long expirationMs) {
    EMAIL_TOKEN_EXPIRATION_MS = expirationMs;
  }

  @Value("${jwt.secret}")
  private void setAlgorithm(String secret) {
    ALGORITHM = Algorithm.HMAC512(secret);
  }
}
