package com.aspacelife.bookingApp.config;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfig {

  @Bean
  public Vertx vertx() {
    return Vertx.vertx();
  }

  @Bean
  public Router router(Vertx vertx) {
    return Router.router(vertx);
  }
}
