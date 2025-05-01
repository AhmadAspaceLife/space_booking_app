package com.aspacelife.spaceBookingApp.booking.restController.space;

import org.springframework.stereotype.Controller;

import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception.SpaceExistsException;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dal.DalSpace;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto.Space;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.dto.CreateSpaceDto;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.resource.SpaceResource;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:12:47
 */

@Controller
public class CreateSpaceController implements Handler<RoutingContext> {
  private final DalSpace dalSpace;

  public CreateSpaceController(final DalSpace dalSpace, final Router router) {
    this.dalSpace = dalSpace;
    router.post("/space")
      .handler(BodyHandler.create())
      .handler(this);
  }

  @Override
  public void handle(final RoutingContext context) {
    RoutingContextUtil.validateBodyPayload(context, CreateSpaceDto.class);
    if (context.response().ended()) return;

    final CreateSpaceDto createSpaceDto = context.get("validatedBody");
    final Space space;
    try {
      space = this.dalSpace.createSpace(createSpaceDto.name(), createSpaceDto.available());
      RoutingContextUtil.sendJsonResponse(context,200, new SpaceResource(space));
    }
    catch (final SpaceExistsException e) {
      RoutingContextUtil.respondError(context,409, e.getMessage());
    }
  }

}
