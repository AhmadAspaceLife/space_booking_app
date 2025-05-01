package com.aspacelife.spaceBookingApp.booking.restController.space;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dal.DalSpace;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto.Space;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dto.User;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:12:51
 */

@Controller
public class FetchSpacesController implements Handler<RoutingContext> {
  private final DalSpace dalSpace;

  public FetchSpacesController(final DalSpace dalSpace, final Router router) {
    this.dalSpace = dalSpace;
    router.get("/space").handler(this);
  }

  @Override
  public void handle(final RoutingContext context) {
    final int page = RoutingContextUtil.parseQueryParam(context, "page", 1);
    final int size = RoutingContextUtil.parseQueryParam(context, "size", 10);

    if (!RoutingContextUtil.validatePagination(context,page,size)) {
      return;
    }

    final String availabilityParam = context.request().getParam("availability");
    final boolean filterByAvailability = availabilityParam != null;
    final boolean availabilityValue = Boolean.parseBoolean(availabilityParam);

    final List<Space> allSpaces = this.dalSpace.getSpaces().stream()
                                               .filter(space -> !filterByAvailability || space.isAvailable() == availabilityValue)
                                               .sorted(Comparator.comparing(Space::getName))
                                               .toList();

    final List<Space> paginatedSpaces = allSpaces.stream()
                                                .skip(size * (page - 1L))
                                                .limit(size)
                                                .toList();




    final JsonObject response = new JsonObject()
                                  .put("page", page)
                                  .put("size", size)
                                  .put("totalSpaces", allSpaces.size())
                                  .put("totalPages", allSpaces.size() / size)
                                  .put("spaces", new JsonArray(paginatedSpaces));

    context.response()
           .putHeader("Content-Type", "application/json")
           .end(response.encode());
  }
}
