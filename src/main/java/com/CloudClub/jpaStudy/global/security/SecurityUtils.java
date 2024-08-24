package com.CloudClub.jpaStudy.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public final class SecurityUtils {

  public static Long getCurrentMemberId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return 0L;
    }
    User principal = (User) authentication.getPrincipal();
    return Long.valueOf(principal.getUsername());
  }
}
