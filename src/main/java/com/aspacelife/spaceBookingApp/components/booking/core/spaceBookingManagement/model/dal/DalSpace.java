package com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal;

import java.util.Optional;
import java.util.Set;

import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.SpaceExistsException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Space;

import lombok.NonNull;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:11:47
 */

public interface DalSpace {

  Set<Space> getSpaces();

  Optional<Space> getSpace(String spaceName);

  Space createSpace(@NonNull final String name, @NonNull final boolean availability) throws SpaceExistsException;

  void addBookingForSpace(@NonNull final Booking booking) throws NoSuchSpaceException;

  Set<Booking> getBookings(@NonNull String spaceId) throws NoSuchSpaceException;


}
