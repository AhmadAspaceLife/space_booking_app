package com.aspacelife.spaceBookingApp.common.exception;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/29/25
 * Time:11:28
 */

@Getter
public class NoSuchParamException extends Exception{
  private final String message;

  public NoSuchParamException(final String message) {
    super(message);
    this.message = message;
  }

}
