package com.CloudClub.jpaStudy.global.security.filters;

import static com.CloudClub.jpaStudy.global.jwt.TokenUtils.parseClaims;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.CloudClub.jpaStudy.global.jwt.TokenProvider;
import com.CloudClub.jpaStudy.global.jwt.TokenUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String accessToken = resolveToken(request);
    System.out.println(accessToken);
    // accessToken 검증
    if (validateToken(accessToken)) {
      setAuthentication(accessToken);
    } else {
      // 만료되었을 경우 accessToken 재발급
      if (accessToken == null) {
        filterChain.doFilter(request, response);
        return;
      }
      String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken);

      if (StringUtils.hasText(reissueAccessToken)) {
        setAuthentication(reissueAccessToken);

        // 재발급된 accessToken 다시 전달
        response.setHeader(AUTHORIZATION, "Bearer " + reissueAccessToken);
      }
    }
    filterChain.doFilter(request, response);
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }
    Claims claims = parseClaims(token);
    return claims.getExpiration().after(new Date());
  }

  private void setAuthentication(String accessToken) {
    Authentication authentication = TokenUtils.getAuthentication(accessToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String resolveToken(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION);
    if (ObjectUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
      return null;
    }
    return token.substring(7);
  }
}
