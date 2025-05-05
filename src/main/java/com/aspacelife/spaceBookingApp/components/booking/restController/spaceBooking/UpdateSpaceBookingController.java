package com.aspacelife.spaceBookingApp.components.booking.restController.spaceBooking;

import org.springframework.stereotype.Controller;

import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.BookingNotFoundException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.BookingOverlapsException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.InvalidBookingTime;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal.DalBooking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.rest.dto.UpdateBookingDto;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.rest.resource.BookingResource;
import com.aspacelife.spaceBookingApp.common.exception.NoSuchParamException;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author AHMAD BUBA
 * Date:4/30/25
 * Time:16:53
 */

@Controller
public class UpdateSpaceBookingController implements Handler<RoutingContext> {
  private final DalBooking dalBooking;

  public UpdateSpaceBookingController(final DalBooking dalBooking, final Router router) {
    this.dalBooking = dalBooking;
    router.patch("/spaces/:id/bookings")
          .handler(BodyHandler.create())
          .handler(this);
  }

  @Override
  public void handle(final RoutingContext context) {
    System.out.println(context.request().getParam("id"));
    System.out.println(context.body().asJsonObject().toString());
    RoutingContextUtil.validateBodyPayload(context, UpdateBookingDto.class);
    if (context.response().ended()) return;

    final UpdateBookingDto updateBookingDto = context.get("validatedBody");
    String bookingId = null;
    try {
      bookingId = RoutingContextUtil.parseParam(context, "id");
    }
    catch (final NoSuchParamException e) {
      RoutingContextUtil.respondError(context, 400, e.getMessage());
      return;
    }
    final Booking booking;
    try {
      booking = this.dalBooking
                  .updateBooking(
                    bookingId,
                    updateBookingDto.spaceName(),
                    updateBookingDto.date(),
                    updateBookingDto.startTime(),
                    updateBookingDto.endTime()
                  );
    }
    catch (final NoSuchSpaceException | BookingNotFoundException e) {
      RoutingContextUtil.respondError(context,404, e.getMessage());
      return;
    }
    catch (final BookingOverlapsException e) {
      RoutingContextUtil.respondError(context, 409, e.getMessage());
      return;
    }
    catch (final InvalidBookingTime e) {
      RoutingContextUtil.respondError(context, 400, e.getMessage());
      return;
    }
    RoutingContextUtil.sendJsonResponse(context,200, new BookingResource(booking));
  }

}
