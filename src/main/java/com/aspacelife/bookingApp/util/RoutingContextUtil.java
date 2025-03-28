package com.aspacelife.bookingApp.util;

import io.vertx.ext.web.RoutingContext;

public final class RoutingContextUtil {
  private RoutingContextUtil() {}


  public static int parseQueryParam(final RoutingContext context, final String param, final int defaultValue) {
    final String value = context.request().getParam(param);
    try {
      return value != null ? Integer.parseInt(value) : defaultValue;
    } catch (final NumberFormatException e) {
      return defaultValue;
    }
  }
}
