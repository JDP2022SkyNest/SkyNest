package com.htecgroup.skynest.util;

import com.htecgroup.SkyNest.model.enitity.UserEntity;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtEmailVerificationUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtEmailVerificationUtils.class);

  @Value("${backend.app.jwtEmailVerificationSecret}")
  private String jwtEmailVerificationSecret;

  @Value("${backend.app.jwtEmailVerificationExpirationMs}")
  private int jwtEmailVerificationExpirationMs;

  public String generateJwtEmailVerificationToken(UserEntity userEntity) {
    return Jwts.builder()
        .setSubject((userEntity.getEmail()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtEmailVerificationExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtEmailVerificationSecret)
        .compact();
  }

  public String getEmailFromJwtEmailVerificationToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtEmailVerificationSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtEmailVerificationSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
