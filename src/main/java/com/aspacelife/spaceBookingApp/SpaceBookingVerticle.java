package com.aspacelife.spaceBookingApp;

import com.aspacelife.spaceBookingApp.common.config.properties.ServerConfigProps;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/* improve code with design patterns and solid principle  */
@Component
@RequiredArgsConstructor
public class SpaceBookingVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(SpaceBookingVerticle.class);
  private final Router router;
  private final ServerConfigProps configProps;


  @Override
  public void start(final Promise<Void> startPromise) {

    this.vertx.createHttpServer()
              .requestHandler(this.router)
              .listen(this.configProps.getPort())
              .onSuccess(ok -> {
                LOGGER.info("Hello from Vert.x");
                LOGGER.info("HTTP server running: http://127.0.0.1:" + this.configProps.getPort());
                startPromise.complete();
              })
              .onFailure(startPromise::fail);
  }

}
