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
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Log4j2
@Component
public class JwtUtils {

  public static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
  public static final String TOKEN_PREFIX = "Bearer ";
  private static final String EMAIL_TOKEN_CLAIM = "Email token";
  private static final String PASSWORD_RESET_PURPOSE = "password reset";
  private static final String EMAIL_VERIFICATION_PURPOSE = "verification";

  public static long ACCESS_TOKEN_EXPIRATION_MS;
  public static long EMAIL_TOKEN_EXPIRATION_MS;
  public static Algorithm ALGORITHM;

  public static String generate(
      String subject,
      Map<String, String> payload,
      long msUntilExpiration,
      String claimName,
      List<String> claims) {
    return JWT.create()
        .withSubject(subject)
        .withPayload(payload)
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

  public static long stillValidForInMs(String token) {
    try {
      DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);
      Date expiresAt = decodedJWT.getExpiresAt();
      Date now = new Date(System.currentTimeMillis());
      return expiresAt.getTime() - now.getTime();
    } catch (JWTVerificationException e) {
      return 0;
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

  public static String validateEmailToken(String token, String purpose) {
    try {
      Verification verification = JWT.require(ALGORITHM);
      JWTVerifier verifier = verification.withClaim(EMAIL_TOKEN_CLAIM, purpose).build();
      DecodedJWT decodedJWT = verifier.verify(token);
      return decodedJWT.getSubject();
    } catch (JWTVerificationException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new UserException(UserExceptionType.INVALID_TOKEN);
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new UserException("Access token is missing", HttpStatus.BAD_REQUEST);
    }
  }

  public static String getValidatedPasswordResetTokenContext(String token) {
    return validateEmailToken(token, JwtUtils.PASSWORD_RESET_PURPOSE);
  }

  public static String getValidatedEmailVerificationTokenContext(String token) {
    return validateEmailToken(token, JwtUtils.EMAIL_VERIFICATION_PURPOSE);
  }

  public static String generatePasswordResetToken(String emailAddress) {
    return generateEmailToken(emailAddress, PASSWORD_RESET_PURPOSE);
  }

  public static String generateEmailVerificationToken(String emailAddress) {
    return generateEmailToken(emailAddress, EMAIL_VERIFICATION_PURPOSE);
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
