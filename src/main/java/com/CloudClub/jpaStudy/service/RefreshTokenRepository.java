package com.CloudClub.jpaStudy.service;

import com.CloudClub.jpaStudy.domain.RefreshToken;

public interface RefreshTokenRepository {

  void save(String username, String refreshToken, Long expiration);

  RefreshToken findByUsername(String username);

}
