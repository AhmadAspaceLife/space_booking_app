package com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:16:07
 */

@Getter
public class NoSuchSpaceException extends Exception {
  private final String message;

  public NoSuchSpaceException(final String message) {
    super(message);
    this.message = message;
  }

}
