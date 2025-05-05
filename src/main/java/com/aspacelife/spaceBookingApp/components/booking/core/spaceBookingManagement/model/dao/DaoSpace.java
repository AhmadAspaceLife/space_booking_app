package com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.SpaceExistsException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal.DalSpace;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Space;

import io.vertx.core.impl.ConcurrentHashSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:11:47
 */

@Repository
@RequiredArgsConstructor
public class DaoSpace implements DalSpace {
  private final Set<Space> spaces = new ConcurrentHashSet<>();

  @Override
  public Set<Space> getSpaces() {
    return this.spaces;
  }

  @Override
  public Optional<Space> getSpace(final String spaceName) {
    return this.spaces.stream()
                      .filter(space -> space.getName().equalsIgnoreCase(spaceName))
                      .findFirst();
  }

  @Override
  public Space createSpace(final @NonNull String name, @NonNull final boolean availability) throws SpaceExistsException {
    final Space newSpace = new Space(name,availability);
    final boolean added = this.spaces.add(newSpace);

    if (!added) {
      throw new SpaceExistsException("Space with name: " + name + " all ready exists");
    }

    return newSpace;
  }

  @Override
  public void addBookingForSpace(final @NonNull Booking booking) throws NoSuchSpaceException {
    final Space space = this.getSpace(booking.getSpaceName())
                            .orElseThrow(() -> new NoSuchSpaceException("Space with the name: " + booking.getSpaceName() + "does not exist"));
    space.addBooking(booking);
  }

  @Override
  public Set<Booking> getBookings(@NonNull final String spaceId) throws NoSuchSpaceException {
    final Space theSpace = this.getSpaces().stream()
                               .filter(space -> space.getId().equals(spaceId))
                               .findFirst()
                             .orElseThrow(() -> new NoSuchSpaceException("No space found with the specified id"));
    return theSpace.getBookings();
  }

}
