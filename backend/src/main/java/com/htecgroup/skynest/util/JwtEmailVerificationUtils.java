package com.htecgroup.skynest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtEmailVerificationUtils {
  @Value("${backend.app.jwtEmailVerificationSecret}")
  private String jwtEmailVerificationSecret;

  @Value("${backend.app.jwtEmailVerificationExpirationMs}")
  private int jwtEmailVerificationExpirationMs;

  @Value("${backend.app.confirmationLink}")
  private String confirmationLink;

  public String generateJwtEmailVerificationToken(String email) {
    return JWT.create()
        .withSubject(email)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date((new Date()).getTime() + jwtEmailVerificationExpirationMs))
        .sign(Algorithm.HMAC256(jwtEmailVerificationSecret));
  }

  public String getEmailFromJwtEmailVerificationToken(String token) {
    return JWT.require(Algorithm.HMAC256(jwtEmailVerificationSecret))
        .build()
        .verify(token)
        .getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      Date expiresAt =
          JWT.require(Algorithm.HMAC256(jwtEmailVerificationSecret))
              .build()
              .verify(token)
              .getExpiresAt();
      JWTVerifier verifier =
          JWT.require(Algorithm.HMAC256(jwtEmailVerificationSecret))
              .acceptExpiresAt(expiresAt.getTime())
              .build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public String getConfirmationLink() {
    return confirmationLink;
  }

  public String buildEmail(String email, String link) {
    return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n"
        + "\n"
        + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n"
        + "\n"
        + "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
        + "    <tbody><tr>\n"
        + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n"
        + "        \n"
        + "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
        + "          <tbody><tr>\n"
        + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
        + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
        + "                  <tbody><tr>\n"
        + "                    <td style=\"padding-left:10px\">\n"
        + "                  \n"
        + "                    </td>\n"
        + "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
        + "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
        + "                    </td>\n"
        + "                  </tr>\n"
        + "                </tbody></table>\n"
        + "              </a>\n"
        + "            </td>\n"
        + "          </tr>\n"
        + "        </tbody></table>\n"
        + "        \n"
        + "      </td>\n"
        + "    </tr>\n"
        + "  </tbody></table>\n"
        + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
        + "    <tbody><tr>\n"
        + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
        + "      <td>\n"
        + "        \n"
        + "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
        + "                  <tbody><tr>\n"
        + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
        + "                  </tr>\n"
        + "                </tbody></table>\n"
        + "        \n"
        + "      </td>\n"
        + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n"
        + "    </tr>\n"
        + "  </tbody></table>\n"
        + "\n"
        + "\n"
        + "\n"
        + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
        + "    <tbody><tr>\n"
        + "      <td height=\"30\"><br></td>\n"
        + "    </tr>\n"
        + "    <tr>\n"
        + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
        + "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
        + "        \n"
        + "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
        + email
        + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
        + link
        + "\">Activate Now</a> </p></blockquote>\n Link will expire in 30 minutes. <p>See you soon</p>"
        + "        \n"
        + "      </td>\n"
        + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
        + "    </tr>\n"
        + "    <tr>\n"
        + "      <td height=\"30\"><br></td>\n"
        + "    </tr>\n"
        + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n"
        + "\n"
        + "</div></div>";
  }
}