package com.aspacelife.bookingApp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "server")
@Component
@Data
public class ServerConfigProps {
  private int port;
}
