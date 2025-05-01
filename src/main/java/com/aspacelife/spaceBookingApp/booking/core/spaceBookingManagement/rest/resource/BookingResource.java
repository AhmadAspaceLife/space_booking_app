package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.resource;

import java.time.LocalDate;
import java.time.LocalTime;

import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto.Booking;

/**
 * @author AHMAD BUBA
 * Date:4/16/25
 * Time:09:39
 */

public record BookingResource(
  String id,
  String spaceName,
  String userEmail,
  String username,
  LocalDate date,
  LocalTime startTime,
  LocalTime endTime,
  boolean checkedIn
) {

  public BookingResource(final Booking booking) {
    this(
      booking.getId(),
      booking.getSpaceName(),
      booking.getUserEmail(),
      booking.getUsername(),
      booking.getDate(),
      booking.getStartTime(),
      booking.getEndTime(),
      booking.isCheckedIn()
    );
  }
}
