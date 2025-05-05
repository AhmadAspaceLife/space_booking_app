package com.aspacelife.spaceBookingApp.components.booking.core.userManagement.rest.resource;

import com.aspacelife.spaceBookingApp.components.booking.core.userManagement.model.dto.User;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:09:46
 */

public record CreateUserResource(String name, String email) {
  public CreateUserResource(final User user) {
    this(user.getEmail(), user.getName());
  }
}
