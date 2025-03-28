package com.aspacelife.bookingApp.controllers.user;

import com.aspacelife.bookingApp.model.dal.DalUser;
import com.aspacelife.bookingApp.util.RoutingContextUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class FetchUsersController implements Handler<RoutingContext> {
  private final DalUser dalUser;

  public FetchUsersController(Router router, DalUser dalUser) {
    router.get("/users").handler(this);
    this.dalUser = dalUser;
  }

  @Override
  public void handle(RoutingContext context) {
    final int page = RoutingContextUtil.parseQueryParam(context, "page", 1);
    final int size = RoutingContextUtil.parseQueryParam(context, "size", 10);

    if (page < 1 || size < 1) {
      context.response()
        .setStatusCode(400)
        .putHeader("Content-Type", "application/json")
        .end(new JsonObject().put("error", "Page and size must be greater than 0").encode());
      return;
    }

    Set<String> savedUsers = this.dalUser.getUsers();
    final List<String> paginatedUsers = savedUsers.stream()
      .sorted()
      .skip(size * (page - 1L))
      .limit(size)
      .collect(Collectors.toList());


    final JsonObject response = new JsonObject()
      .put("page", page)
      .put("size", size)
      .put("totalUsers", savedUsers.size())
      .put("totalPages", savedUsers.size() / size)
      .put("users", new JsonArray(paginatedUsers));

    context.response()
      .putHeader("Content-Type", "application/json")
      .end(response.encode());
  }
}
