package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/30/25
 * Time:17:08
 */

@Getter
public class BookingNotFoundException extends Exception{
  private final String message;

  public BookingNotFoundException(final String message) {
    super(message);
    this.message = message;
  }
}
