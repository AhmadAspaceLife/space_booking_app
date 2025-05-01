package com.aspacelife.spaceBookingApp.booking.core.userManagement.exception;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:09:40
 */

@Getter
public class UserExistsException extends Exception {
  private final String message;

  public UserExistsException(final @NonNull String message) {
    super(message);
    this.message = message;
  }

}
