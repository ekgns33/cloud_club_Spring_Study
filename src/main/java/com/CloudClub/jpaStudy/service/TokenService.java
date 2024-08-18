package com.CloudClub.jpaStudy.service;

import com.CloudClub.jpaStudy.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public void saveRefreshToken(String username, String refreshToken, Long expiration) {
    refreshTokenRepository.save(username, refreshToken, expiration);
  }

  public void validateRefreshToken(String username, String refreshToken) {
    RefreshToken actual = findRefreshTokenByUserName(username);
    actual.validate(refreshToken);
  }

  public RefreshToken findRefreshTokenByUserName(String username) {
    return refreshTokenRepository.findByUsername(username);
  }

}
