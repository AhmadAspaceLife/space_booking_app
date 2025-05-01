package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto;

import java.util.Set;
import java.util.UUID;

import io.vertx.core.impl.ConcurrentHashSet;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AHMAD BUBA
 * Date:3/17/25
 * Time:11:41
 */


@Getter
/* convert to immutable */
public class Space {

  private final String id;
  @Setter
  private String name;
  @Setter
  private boolean available;
  private final Set<Booking> bookings = new ConcurrentHashSet<>();

  public Space(final String name, final boolean available) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.available = available;
  }

  public void addBooking(final Booking booking) {
    this.bookings.add(booking);
  }


  @Override
  public boolean equals(final Object other) {
    return other instanceof Space
           && ((Space) other).getName()
                                  .equals(this.getName());
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

}
