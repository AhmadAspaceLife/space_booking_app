package com.aspacelife.bookingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
  public static void main(final String[] args) {
    final SpringApplication app = new SpringApplication(Main.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }
}
