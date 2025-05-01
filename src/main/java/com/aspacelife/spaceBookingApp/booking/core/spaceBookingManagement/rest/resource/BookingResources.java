package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.resource;

import java.util.List;

import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto.Booking;

/**
 * @author AHMAD BUBA
 * Date:4/29/25
 * Time:11:56
 */

public record BookingResources(
  List<BookingResource> bookings
) {
  public static BookingResources from(final List<Booking> bookingList) {
    final List<BookingResource> mapped = bookingList.stream()
                                                    .map(BookingResource::new)
                                                    .toList();
    return new BookingResources(mapped);
  }
}

