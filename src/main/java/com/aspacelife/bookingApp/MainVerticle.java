package com.aspacelife.bookingApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.aspacelife.bookingApp.config.ServerConfigProps;
import com.aspacelife.bookingApp.model.dto.Booking;
import com.aspacelife.bookingApp.model.dto.Space;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/* improve code with design patterns and solid principle  */
@Component
@RequiredArgsConstructor
public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);
  private final Router router;
  private final ServerConfigProps configProps;

  private final Set<String> users =  new HashSet<>();
  private final Set<Space> spaces = ConcurrentHashMap.newKeySet();
  private final Set<Booking> bookings = ConcurrentHashMap.newKeySet();

  @Override
  public void start(final Promise<Void> startPromise) {

    this.vertx.createHttpServer()
              .requestHandler(router)
              .listen(this.configProps.getPort())
              .onSuccess(ok -> {
                LOGGER.info("HTTP server running: http://127.0.0.1:" + configProps.getPort());
                startPromise.complete();
              })
              .onFailure(startPromise::fail);

    router.post("/users").handler(BodyHandler.create()).handler(this::createUser);
    router.get("/spaces").handler(this::getSpaces);
    router.post("/spaces").handler(BodyHandler.create()).handler(this::createSpace);
    router.get("/spaces/:name").handler(this::getSpaceByName);
    router.get("/bookings").handler(this::getBookings);
    router.get("/spaces/:id/bookings").handler(this::getSpaceBookings);
    router.get("/users/:id/bookings").handler(this::getUserBookings);
    router.post("/bookings").handler(BodyHandler.create()).handler(this::createBooking);
    router.put("/bookings/:id").handler(BodyHandler.create()).handler(this::updateBooking);
    router.get("/spaces/:id").handler(this::getAvailableSlots);
  }


  private void createUser(final RoutingContext context) {
    /* wrong {wrap in try and catch ???[what if user send multipart]} */
    final JsonObject body = context.body().asJsonObject();
    if (body == null) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Invalid request body").encode());
      return;
    }


    final String username = body.getString("username");

    if (username == null || username.isBlank()) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Username is required").encode());
      return;
    }

    if (this.users.contains(username)) {
      context.response().setStatusCode(409).end("Username already exists");
      return;
    }

    this.users.add(username);
    context.response()
           .putHeader("Content-Type", "application/json")
           .end(new JsonObject().put("message", "User "+ username +  " registered successfully")
                                .encode()
           );
  }

  private void getUsers(final RoutingContext context) {

  }


  private void createBooking(final RoutingContext context) {

    /* wrong */
    final JsonObject body = context.body().asJsonObject();

    if (body == null) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Invalid request body").encode());
      return;
    }

    final String username = body.getString("username");
    final String spaceName = body.getString("space_name");
    final String startTimeStr = body.getString("start_time");
    final String endTimeStr = body.getString("end_time");


    if (username == null || username.isBlank() || spaceName == null || spaceName.isBlank() || startTimeStr == null || startTimeStr.isBlank() || endTimeStr == null || endTimeStr.isBlank()) {
      context.response()
             .setStatusCode(400)
        /* create static content-type */
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Invalid payload, please check username, spacename, startime and endtime have been passed properly").encode());
      return;
    }

    final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    final LocalDateTime startTime;
    final LocalDateTime endTime;

    try {
      startTime = LocalDateTime.parse(startTimeStr, formatter);
      endTime = LocalDateTime.parse(endTimeStr, formatter);
    } catch (final DateTimeParseException e) {
      context.response()
             .setStatusCode(400)
        /* create static content-type */
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Invalid date format").encode());
      return;
    }

    if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Start time must be before end time").encode());
      return;
    }


    if (!this.users.contains(username)) {
      context.response().setStatusCode(409).end("No such user");
      return;
    }

    final Optional<Space> spaceOptional = this.spaces.stream()
                                                     .filter(space -> space.getName().equals(spaceName))
                                                     .findFirst();

    if (spaceOptional.isEmpty()) {
      context.response()
             .setStatusCode(409)
        /* create static content-type */
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "No such space").encode());
      return;
    }

    final Space space = spaceOptional.get();

    final Booking newBooking = new Booking(UUID.randomUUID().toString(), username, space, startTime, endTime, BookingStatus.CONFIRMED);

    for (final Booking existing : this.bookings) {
      if (newBooking.overlapsWith(existing)) {
        /* wrong [return custom error not crash the server ] */
        throw new IllegalStateException("Booking conflicts with an existing reservation.");
      }
    }

    this.bookings.add(newBooking);
    context.response()
           .setStatusCode(201)
           .putHeader("Content-Type", "application/json")
           .end(new JsonObject().put("message", "Booking created successfully").encode());
  }

  private void createSpace(final RoutingContext context) {
    final JsonObject body = context.body().asJsonObject();

    if (body == null) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Invalid request body").encode());
      return;
    }

    final String spaceName = body.getString("name");
    if (spaceName == null || spaceName.isBlank()) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Spacename is required").encode());
      return;
    }

    final Space space = new Space(UUID.randomUUID().toString(), spaceName, true);

    if (this.spaces.contains(space)) {
      context.response().setStatusCode(409).end("Spacename already exists");
      return;
    }

    this.spaces.add(space);



    context.response()
           .putHeader("Content-Type", "application/json")
           .end(space.toJson().encode());

  }

  private void getSpaces(final RoutingContext context) {
    final int page = this.parseQueryParam(context, "page", 1);
    final int size = this.parseQueryParam(context, "size", 10);

    if (page < 1 || size < 1) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Page and size must be greater than 0").encode());
      return;
    }

    final List<Space> sortedSpaces = this.spaces.stream()
                                                .sorted(Comparator.comparing(Space::getName))
                                                .toList();

    final int fromIndex = (page - 1) * size;
    final int toIndex = Math.min(fromIndex + size, sortedSpaces.size());

    if (fromIndex >= sortedSpaces.size()) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "No spaces found for the given page").encode());
      return;
    }

    final List<Space> paginatedSpaces = sortedSpaces.subList(fromIndex, toIndex);

    final JsonObject response = new JsonObject()
                                  .put("page", page)
                                  .put("size", size)
                                  .put("totalSpaces", this.spaces.size())
                                  .put("totalPages", (int) Math.ceil((double) this.spaces.size() / size))
                                  .put("spaces", new JsonArray(paginatedSpaces.stream()
                                                                              .map(Space::toJson)
                                                                              .toList()));


    context.response()
           .putHeader("Content-Type", "application/json")
           .end(response.encode());
  }

  private void getSpaceByName(final RoutingContext context) {
    final String spaceName = context.pathParam("name");

    if (spaceName == null || spaceName.isBlank()) {
      context.response()
             .setStatusCode(400)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Space name is required").encode());
      return;
    }

    final Optional<Space> space = this.spaces.stream()
                                             .filter(s -> s.getName().equalsIgnoreCase(spaceName))
                                             .findFirst();

    if (space.isEmpty()) {
      context.response()
             .setStatusCode(404)
             .putHeader("Content-Type", "application/json")
             .end(new JsonObject().put("error", "Space not found").encode());
      return;
    }

    context.response()
           .setStatusCode(200)
           .putHeader("Content-Type", "application/json")
           .end(space.get().toJson().encode());
  }


  private void getUserBookings(RoutingContext context) {}

  private void getAvailableSlots(RoutingContext context) {}

  private void updateBooking(RoutingContext context) {}

  private void getSpaceBookings(RoutingContext context) {}

  private void getBookings(RoutingContext context) {}

  private int parseQueryParam(final RoutingContext context, final String param, final int defaultValue) {
    final String value = context.request().getParam(param);
    try {
      return value != null ? Integer.parseInt(value) : defaultValue;
    } catch (final NumberFormatException e) {
      return defaultValue;
    }
  }

}
