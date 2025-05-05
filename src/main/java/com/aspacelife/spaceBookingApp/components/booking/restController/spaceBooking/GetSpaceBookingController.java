package com.aspacelife.spaceBookingApp.components.booking.restController.spaceBooking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal.DalBooking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal.DalSpace;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.rest.resource.BookingResources;
import com.aspacelife.spaceBookingApp.common.exception.NoSuchParamException;
import com.aspacelife.spaceBookingApp.common.util.RoutingContextUtil;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author AHMAD BUBA
 * Date:4/29/25
 * Time:10:49
 */

@Controller
public class GetSpaceBookingController implements Handler<RoutingContext> {
  private final DalSpace dalSpace;

  public GetSpaceBookingController(final DalBooking dalBooking,final DalSpace dalSpace, final Router router) {
    this.dalSpace = dalSpace;
    router.get("/spaces/:id/bookings").handler(this);
  }

  @Override
  public void handle(final RoutingContext context) {
    System.out.println(context.request().getParam("id"));
    final int page = RoutingContextUtil.parseQueryParam(context, "page", 1);
    final int size = RoutingContextUtil.parseQueryParam(context, "size", 10);
    if (!RoutingContextUtil.validatePagination(context, page, size)) {
      return;
    }
    final String fromParam = context.request().getParam("from"); // format: yyyy-MM-dd
    final String toParam = context.request().getParam("to");
    LocalDate fromDate = null;
    LocalDate toDate = null;

    try {
      if (fromParam != null) {
        fromDate = LocalDate.parse(fromParam);
      }
      if (toParam != null) {
        toDate = LocalDate.parse(toParam);
      }
    } catch (DateTimeParseException e) {
      RoutingContextUtil.respondError(context, 400, "Invalid date format. Use yyyy-MM-dd");
      return;
    }
    String spaceId = null;
    try {
      spaceId = RoutingContextUtil.parseParam(context, "id");
    }
    catch (final NoSuchParamException e) {
      RoutingContextUtil.respondError(context, 400, e.getMessage());
      return;
    }
    try {
      final LocalDate finalFromDate = fromDate;
      final LocalDate finalToDate = toDate;
      final List<Booking> filteredBookings = this.dalSpace.getBookings(spaceId).stream()
                                                          .filter(booking -> {
                                                                                final boolean withinFrom = finalFromDate == null || !booking.getDate().isBefore(finalFromDate);
                                                                                final boolean withinTo = finalToDate == null || !booking.getDate().isAfter(finalToDate);
                                                                                return withinFrom && withinTo;
                                                          })
                                                          .sorted(Comparator.comparing(Booking::getDate).reversed())
                                                          .toList();

      final List<Booking> paginated = filteredBookings.stream()
                                                      .skip(size * (page - 1L))
                                                      .limit(size)
                                                      .toList();

      RoutingContextUtil.sendPaginatedJsonResponse(context, page, size, filteredBookings.size(), "bookings", BookingResources.from(paginated).bookings());
    }
    catch (final NoSuchSpaceException e) {
      RoutingContextUtil.respondError(context,400, e.getMessage());
    }
  }

}

