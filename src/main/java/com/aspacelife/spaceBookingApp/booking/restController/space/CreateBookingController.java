package com.aspacelife.spaceBookingApp.booking.restController.space;

import org.springframework.stereotype.Controller;

import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception.BookingOverlapsException;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception.InvalidBookingTime;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dal.DalBooking;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.dto.CreateBookingDto;
import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.resource.BookingResource;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.exception.NoSuchUserException;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:15:38
 */

@Controller
public class CreateBookingController implements Handler<RoutingContext> {
  private final DalBooking dalBooking;

  public CreateBookingController(final DalBooking dalBooking, final Router router) {
    this.dalBooking = dalBooking;
    router.post("/bookSpace").handler(BodyHandler.create())
      .handler(this);
  }

  @Override
  public void handle(final RoutingContext context) {
    System.out.println(context.body().asJsonObject().toString());
    RoutingContextUtil.validateBodyPayload(context, CreateBookingDto.class);
    if (context.response().ended()) return;
    final CreateBookingDto createBookingDto = context.get("validatedBody");
    final Booking booking;
    try {
      booking = this.dalBooking
        .createBooking(
          createBookingDto.userEmail(),
          createBookingDto.username(),
          createBookingDto.spaceName(),
          createBookingDto.date(),
          createBookingDto.startTime(),
          createBookingDto.endTime()
        );
    }
    catch (final NoSuchSpaceException e) {
      RoutingContextUtil.respondError(context,404, e.getMessage());
      return;
    }
    catch (final BookingOverlapsException e) {
      RoutingContextUtil.respondError(context, 409, e.getMessage());
      return;
    }
    catch (final InvalidBookingTime| NoSuchUserException e) {
      RoutingContextUtil.respondError(context, 400, e.getMessage());
      return;
    }
    RoutingContextUtil.sendJsonResponse(context,200, new BookingResource(booking));
  }

}
