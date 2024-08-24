package com.CloudClub.jpaStudy.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

  public static SecretKey STATIC_SECRET_KEY;
  private final String secretKey;

  public TokenUtils(@Value("${jwt.secret}")
  String secretKey) {
    this.secretKey = secretKey;
    STATIC_SECRET_KEY = Keys.hmacShaKeyFor(this.secretKey.getBytes());
  }

  public static Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);
    List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public static Claims parseClaims(String token) {
    try {
      System.out.println("parsing");
      return Jwts.parser()
          .verifyWith(STATIC_SECRET_KEY)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (ExpiredJwtException e) {
      System.out.println("ExpiredJwtException");
      return e.getClaims();
    } catch (MalformedJwtException | SecurityException e) {
      System.out.println("MalformedJwtException");
      throw new IllegalStateException();
    } catch (Exception e) {
      System.out.println("Exception");
      throw new IllegalStateException();
    }
  }

  private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
    return List.of(claims.get("role").toString())
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
