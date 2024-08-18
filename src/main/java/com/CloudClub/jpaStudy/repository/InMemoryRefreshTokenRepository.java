package com.CloudClub.jpaStudy.repository;

import com.CloudClub.jpaStudy.domain.RefreshToken;
import com.CloudClub.jpaStudy.service.RefreshTokenRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRefreshTokenRepository implements RefreshTokenRepository {

  private Map<String, RefreshToken> store = new HashMap<>();

  @Override
  public void save(String username, String refreshToken, Long expiration) {
    store.put(username, new RefreshToken(refreshToken, expiration));
  }

  @Override
  public RefreshToken findByUsername(String username) {
    return store.get(username);
  }

}
