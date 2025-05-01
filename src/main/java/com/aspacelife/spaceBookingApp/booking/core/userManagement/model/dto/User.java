package com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dto;

import java.time.Instant;

import lombok.Getter;

/**
 * @author AHMAD BUBA
 * Date:4/1/25
 * Time:09:18
 */

@Getter
public class User {
  private String email;
  private String name;
  private Instant createdAt;

  public User(final String email, final String name) {
    this.email = email;
    this.name = name;
    this.createdAt = Instant.now();
  }

  @Override
  public boolean equals(final Object other) {
    return other instanceof User
           && ((User) other).getEmail()
                            .equals(this.email);
  }

  @Override
  public int hashCode() {
    return this.email.hashCode();
  }

}
