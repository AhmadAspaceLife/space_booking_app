package com.aspacelife.spaceBookingApp.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "server")
@Component
@Setter
@Getter
public class ServerConfigProps {
  private int port;
}
