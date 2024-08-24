package com.CloudClub.jpaStudy.global.jwt;

import com.CloudClub.jpaStudy.service.member.TokenService;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenProvider {

  private static final long ACCESS_TOKEN_VALID_TIME = 1000 * 60 * 30; // 30 min
  private static final long REFRESH_TOKEN_VALID_TIME = 1000 * 60 * 5; // 5 hour
  private static final String KEY_ROLE = "role";

  private final TokenService tokenService;

  public String generateAccessToken(Authentication authentication) {
    return generateToken(authentication, ACCESS_TOKEN_VALID_TIME);
  }

  public String generateRefreshToken(Authentication authentication) {
    String refreshToken = generateToken(authentication, REFRESH_TOKEN_VALID_TIME);
    tokenService.saveRefreshToken(authentication.getName(), refreshToken, REFRESH_TOKEN_VALID_TIME);
    return refreshToken;
  }

  private String generateToken(Authentication authentication, long validTime) {
    Date now = new Date();
    Date expiredDateTime = new Date(now.getTime() + validTime);

    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(KEY_ROLE, authorities)
        .setIssuedAt(now)
        .setExpiration(expiredDateTime)
        .signWith(TokenUtils.STATIC_SECRET_KEY)
        .compact();
  }

  public String reissueAccessToken(String refreshToken) {
    String userName = TokenUtils.getAuthentication(refreshToken).getName();
    tokenService.validateRefreshToken(userName, refreshToken);
    return generateAccessToken(TokenUtils.getAuthentication(refreshToken));
  }

}
