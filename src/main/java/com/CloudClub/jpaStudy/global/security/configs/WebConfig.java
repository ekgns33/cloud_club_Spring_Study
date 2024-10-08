package com.CloudClub.jpaStudy.global.security.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**")
        .addResourceLocations("classpath:/static/css/")
        .setCachePeriod(3600)
        .resourceChain(true);
  }


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // Disable all CORS mappings
    registry.addMapping("/**").allowedOrigins();
  }
}