package com.CloudClub.jpaStudy.auth;

import com.CloudClub.jpaStudy.domain.member.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements OAuth2User, UserDetails {

  private final Member member;
  private final Map<String, Object> attributes;
  private final String attributeKey;

  public PrincipalDetails(Member member, Map<String, Object> attributes, String attributeKey) {
    this.member = member;
    this.attributes = attributes;
    this.attributeKey = attributeKey;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
        new SimpleGrantedAuthority(member.getMemberRole().getKey())
    );
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return String.valueOf(member.getId());
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  @Override
  public String getName() {
    return attributes.get(attributeKey).toString();
  }

  public Long getUserId() {
    return member.getId();
  }
}
