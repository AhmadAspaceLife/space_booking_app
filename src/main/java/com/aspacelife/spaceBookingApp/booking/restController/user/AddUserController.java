package com.aspacelife.spaceBookingApp.booking.restController.user;

import org.springframework.stereotype.Controller;

import com.aspacelife.spaceBookingApp.booking.core.userManagement.exception.UserExistsException;

import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dal.DalUser;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dto.User;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.rest.dto.CreateUserDto;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.rest.resource.CreateUserResource;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author AHMAD BUBA
 * Date:3/31/25
 * Time:13:16
 */


@Controller
public class AddUserController implements Handler<RoutingContext> {
  private final DalUser dalUser;


  public AddUserController(final DalUser dalUser, final Router router) {
    this.dalUser = dalUser;
    router.post("/users")
          .handler(BodyHandler.create())
          .handler(this);
  }


  @Override
  public void handle(final RoutingContext context) {
    RoutingContextUtil.validateBodyPayload(context, CreateUserDto.class);
    // Stop if validation failed (response already sent)
    if (context.response().ended()) return;

    final CreateUserDto dto = context.get("validatedBody");
    final User user;
    try {
      user = this.dalUser.addUser(dto.email(), dto.name());
    }
    catch (final UserExistsException e) {
      RoutingContextUtil.respondError(context,409, e.getMessage());
      return;
    }
    context.response()
      .setStatusCode(200)
      .putHeader("Content-Type", "application/json")
           .end(JsonObject.mapFrom(new CreateUserResource(user)).encode());
  }

}
