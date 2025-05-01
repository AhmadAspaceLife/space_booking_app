package com.aspacelife.spaceBookingApp.booking.core.userManagement.exception;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/18/25
 * Time:11:32
 */

@Getter
public class NoSuchUserException extends Exception{
  private final String message;

  public NoSuchUserException(final String message) {
    super(message);
    this.message = message;
  }
}
