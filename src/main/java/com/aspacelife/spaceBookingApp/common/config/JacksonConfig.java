package com.aspacelife.spaceBookingApp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.vertx.core.json.jackson.DatabindCodec;
import jakarta.annotation.PostConstruct;

/**
 * @author AHMAD BUBA
 * Date:4/18/25
 * Time:14:16
 */

@Component
public class JacksonConfig {

  @PostConstruct
  public void vertxObjectMapper() {
    DatabindCodec.mapper()
                 .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }
}
