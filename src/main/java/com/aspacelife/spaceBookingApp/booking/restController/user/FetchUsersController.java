package com.aspacelife.spaceBookingApp.booking.restController.user;


import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dal.DalUser;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dto.User;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class FetchUsersController implements Handler<RoutingContext> {
  private final DalUser dalUser;

  public FetchUsersController(final Router router, final DalUser dalUser) {
    router.get("/users").handler(this);
    this.dalUser = dalUser;
  }

  @Override
  public void handle(final RoutingContext context) {
    final int page = RoutingContextUtil.parseQueryParam(context, "page", 1);
    final int size = RoutingContextUtil.parseQueryParam(context, "size", 10);

    if (!RoutingContextUtil.validatePagination(context, page, size)) {
      return;
    }

    final Set<User> savedUsers = this.dalUser.getUsers();
    final List<User> paginatedUsers = savedUsers.stream()
      .sorted(Comparator.comparing(User::getCreatedAt))
      .skip(size * (page - 1L))
      .limit(size)
      .collect(Collectors.toList());


    RoutingContextUtil.sendPaginatedJsonResponse(context, page, size, savedUsers.size(), "users", paginatedUsers);
  }
}
