package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:17:25
 */

@Getter
public class BookingOverlapsException extends Exception {
  private final String message;

  public BookingOverlapsException(final String message) {
    super(message);
    this.message = message;
  }

}
