package com.aspacelife.spaceBookingApp.common.startup;

import com.aspacelife.spaceBookingApp.SpaceBookingVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartUp implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartUp.class);
  private final Vertx vertx;
  private final SpaceBookingVerticle spaceBookingVerticle;

  @Override
  public void run(final String... args) throws Exception {
    LOGGER.info("Starting application...");
    this.vertx.deployVerticle(this.spaceBookingVerticle);
  }
}
