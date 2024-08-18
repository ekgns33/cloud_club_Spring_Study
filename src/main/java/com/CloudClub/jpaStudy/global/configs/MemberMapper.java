package com.CloudClub.jpaStudy.global.configs;

import com.CloudClub.jpaStudy.auth.OAuth2UserInfo;
import com.CloudClub.jpaStudy.domain.Member;

public class MemberMapper {

  public static Member mapOAuth2InfoToMember (OAuth2UserInfo oAuth2UserInfo) {
    return Member.builder()
      .name(oAuth2UserInfo.getName())
      .email(oAuth2UserInfo.getEmail())
      .build();
  }

}
