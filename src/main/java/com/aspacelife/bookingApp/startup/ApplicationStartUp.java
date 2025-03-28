package com.aspacelife.bookingApp.startup;

import com.aspacelife.bookingApp.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartUp implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ApplicationStartUp.class);
  private final Vertx vertx;
  private final MainVerticle mainVerticle;

  @Override
  public void run(final String... args) throws Exception {
    this.vertx.deployVerticle(this.mainVerticle)
      .onSuccess(s -> log.info("MainVerticle deployed successfully"))
      .onFailure(t -> log.error("MainVerticle deploy failed", t));
  }
}
