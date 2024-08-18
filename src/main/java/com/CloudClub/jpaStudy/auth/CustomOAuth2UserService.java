package com.CloudClub.jpaStudy.auth;

import static com.CloudClub.jpaStudy.global.configs.MemberMapper.mapOAuth2InfoToMember;

import com.CloudClub.jpaStudy.domain.Member;
import com.CloudClub.jpaStudy.repository.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);
    Member member = fetchAndSaveIfAbsent(oAuth2UserInfo);

    // 6. OAuth2User로 반환
    return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
  }

  private Member fetchAndSaveIfAbsent(OAuth2UserInfo oAuth2UserInfo) {
    return memberRepository.findByEmail(oAuth2UserInfo.getEmail())
        .orElseGet(() -> memberRepository.save(mapOAuth2InfoToMember(oAuth2UserInfo)));
  }

}
