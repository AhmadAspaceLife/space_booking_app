package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.exception;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:12:02
 */

@Getter
public class SpaceExistsException extends Exception {
  private final String message;

  public SpaceExistsException(final @NonNull String message) {
    super(message);
    this.message = message;
  }

}
