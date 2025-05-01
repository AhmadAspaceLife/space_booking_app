package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:17:04
 */

@Getter
public class InvalidBookingTime extends Exception {
  private final String message;

  public InvalidBookingTime(final String message) {
    super(message);
    this.message = message;
  }

}
