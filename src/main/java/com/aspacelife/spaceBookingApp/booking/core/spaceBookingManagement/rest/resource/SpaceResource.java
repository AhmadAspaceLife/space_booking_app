package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.resource;

import com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto.Space;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:14:59
 */

public record SpaceResource(String id, String name, boolean available) {
  public SpaceResource(final Space space) {
    this(space.getId(), space.getName(), space.isAvailable());
  }
}
