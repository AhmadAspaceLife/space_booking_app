package com.aspacelife.spaceBookingApp.common.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.aspacelife.spaceBookingApp.common.exception.NoSuchParamException;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

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

  public static String parseParam(final RoutingContext context, final String param) throws NoSuchParamException {
    final Optional<String> valueOp = Optional.ofNullable(
      context.request().getParam(param)
    );
    return valueOp.orElseThrow(() -> new NoSuchParamException("The " + param + " param is empty"));
  }


  public static <T> void validateBodyPayload(final RoutingContext context, final Class<T> dtoClass) {
    final JsonObject payload;
    try {
      payload = context.body().asJsonObject();
    } catch (final Exception e) {
      respondError(context, 400, "Invalid JSON body");
      return;
    }

    final T dto;
    try {
      dto = payload.mapTo(dtoClass);
    } catch (final Exception e) {
      respondError(context, 400, "Payload mapping failed: " + e.getMessage());
      return;
    }

    try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      final Validator validator = factory.getValidator();
      final Set<ConstraintViolation<T>> violations = validator.validate(dto);

      if (!violations.isEmpty()) {
        final String errorMessages = violations.stream()
                                               .map(ConstraintViolation::getMessage)
                                               .collect(Collectors.joining(", "));
        respondError(context, 400, errorMessages);
        return;
      }

      context.put("validatedBody", dto);
    }
  }

  public static void respondError(final RoutingContext context, final int statusCode, final String message) {
    context.response()
           .setStatusCode(statusCode)
           .putHeader("Content-Type", "application/json")
           .end(new JsonObject().put("error", message).encode());
  }

  public static HttpServerResponse prepareJsonResponse(RoutingContext context, int statusCode) {
    return context.response()
                  .setStatusCode(statusCode)
                  .putHeader("Content-Type", "application/json");
  }

  public static void sendJsonResponse(final RoutingContext context, final int statusCode, final Object body) {
    context.response()
           .setStatusCode(statusCode)
           .putHeader("Content-Type", "application/json")
           .end(JsonObject.mapFrom(body).encode());
  }

  public static void sendPaginatedJsonResponse(
    final RoutingContext context,
    final int page,
    final int size,
    final int totalSize,
    final String totalSuffix,
    final List<?> objectList
  ) {
    final JsonObject response = new JsonObject()
                                  .put("page", page)
                                  .put("size", size)
                                  .put("total_" + totalSuffix, totalSize)
                                  .put("totalPages", totalSize / size)
                                  .put("users", new JsonArray(objectList));
    context.response()
      .setStatusCode(200)
      .putHeader("Content-Type", "application/json")
      .end(response.encode());
  }


  public static boolean validatePagination(final RoutingContext context, final int page, final int size) {
    if (page < 1 || size < 1) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Page and size must be greater than 0").encode());
      return false;
    }
    return true;
  }

}
