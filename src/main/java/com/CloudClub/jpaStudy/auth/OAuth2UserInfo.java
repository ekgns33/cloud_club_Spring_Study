package com.CloudClub.jpaStudy.auth;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2UserInfo {

  private final String name;
  private final String email;
  private final String profile;

  @Builder
  private OAuth2UserInfo(String name, String email, String profile) {
    this.name = name;
    this.email = email;
    this.profile = profile;
  }

  public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
    switch (registrationId) {
      case "kakao":
        return ofKakao(attributes);
      case "google":
        return ofGoogle(attributes);
      default:
        throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
    }
  }

  private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    return OAuth2UserInfo.builder()
        .name((String) profile.get("nickname"))
        .email((String) kakaoAccount.get("email"))
        .profile((String) profile.get("profile_image_url"))
        .build();
  }

  private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
    return OAuth2UserInfo.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .profile((String) attributes.get("picture"))
        .build();
  }
}
