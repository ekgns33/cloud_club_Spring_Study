package com.CloudClub.jpaStudy.global.configs.securities;

import com.CloudClub.jpaStudy.auth.CustomOAuth2UserService;
import com.CloudClub.jpaStudy.auth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final TokenAuthenticationFilter tokenAuthenticationFilter;
  private final TokenExceptionFilter tokenExceptionFilter;
  private final CustomOAuth2UserService oAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
    return web -> web.ignoring()
        // error endpoint를 열어줘야 함, favicon.ico 추가!
        .requestMatchers("/error", "/favicon.ico");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .headers(header -> header.frameOptions(
            FrameOptionsConfig::disable
        ).disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request ->
            request
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/home"),
                    new AntPathRequestMatcher("/success"),
                    new AntPathRequestMatcher("/custom-login"),
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**")// Exclude custom login page from auth
                )
                .permitAll()
                .anyRequest().authenticated()
        )
        .oauth2Login(oauth2 ->
            oauth2
                .loginPage("/custom-login")
                .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                .successHandler(oAuth2SuccessHandler))
        .addFilterBefore(tokenAuthenticationFilter,
          UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass());// 토큰 예외 핸들링

    return http.build();
  }
}
