package com.CloudClub.jpaStudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

  @RequestMapping("/")
  public String home() {

    log.info("home controller");
    return "home";
  }

  @GetMapping("/custom-login")
  public String login() {
    log.info("login controller");
    return "custom-login";
  }

  @GetMapping("/success")
  public String success() {
    log.info("success controller");
    return "success";
  }
}
