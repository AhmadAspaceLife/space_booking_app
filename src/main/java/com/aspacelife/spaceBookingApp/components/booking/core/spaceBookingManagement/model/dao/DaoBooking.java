package com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.BookingNotFoundException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.BookingOverlapsException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.InvalidBookingTime;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal.DalBooking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal.DalSpace;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Space;
import com.aspacelife.spaceBookingApp.components.booking.core.userManagement.exception.NoSuchUserException;
import com.aspacelife.spaceBookingApp.components.booking.core.userManagement.model.dal.DalUser;

import io.vertx.core.impl.ConcurrentHashSet;
import lombok.RequiredArgsConstructor;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:15:42
 */

@Repository
@RequiredArgsConstructor
public class DaoBooking implements DalBooking {
  private final Set<Booking> bookings = new ConcurrentHashSet<>();
  private final DalSpace dalSpace;
  private final DalUser dalUser;

  @Override
  public Booking createBooking(final String userEmail, final String username, final String spaceName, final LocalDate date, final LocalTime startTime, final LocalTime endTime) throws NoSuchSpaceException, InvalidBookingTime, BookingOverlapsException, NoSuchUserException {
    if (!this.isValidBookingTime(startTime, endTime)) {
      throw new InvalidBookingTime("Please check the time, invalid booking time. start time must be before end time and every booking must start and end with 12:00 am and cannot role out");
    }
    if (!this.dalUser.userExists(userEmail)) {
      throw new NoSuchUserException("No user found with this email");
    }
    final Space space = this.dalSpace.getSpace(spaceName)
                                     .orElseThrow(() -> new NoSuchSpaceException("No space found with the name: " + spaceName));
    final List<Booking> bookingForTheDay = Optional.ofNullable(space.getBookings())
                                                   .orElse(Set.of())
                                                   .stream()
                                                   .filter(booking -> booking.getDate().isEqual(date))
                                                   .toList();
    final Booking newBooking = new Booking(userEmail, username, date, startTime, endTime, spaceName);
    final Optional<Booking> overlappingBookingOp = this.isOverlapping(newBooking, bookingForTheDay);
    if (overlappingBookingOp.isPresent()) {
      final Booking overlappingBooking = overlappingBookingOp.get();
      throw new BookingOverlapsException("Your booking overlaps with a booking starting at " + overlappingBooking.getStartTime() + " and ending at " + overlappingBooking.getEndTime());
    }
    this.dalSpace.addBookingForSpace(newBooking);
    this.bookings.add(newBooking);
    return newBooking;
  }

  @Override
  public Optional<Booking> getBooking(final String bookingId) {
    return this.bookings.stream()
                        .filter(booking -> booking.getId().equals(bookingId))
                        .findFirst();
  }


  @Override
  public Set<Booking> getAllBookings() {
    return this.bookings;
  }

  @Override
  public Set<Booking> getAllSpaceBookings(final String spaceName) {
    return Set.of();
  }

  @Override
  public Set<Booking> getAllUserBookings(final String userEmail) {
    return Set.of();
  }


  @Override
  public Booking updateBooking(final String bookingId, final String spaceName, final LocalDate date, final LocalTime startTime, final LocalTime endTime) throws NoSuchSpaceException, InvalidBookingTime, BookingOverlapsException, BookingNotFoundException {
    if (!this.isValidBookingTime(startTime, endTime)) {
      throw new InvalidBookingTime("Please check the time, invalid booking time. start time must be before end time and every booking must start and end with 12:00 am and cannot role out");
    }
    final Booking theBooking = this.bookings.stream()
                                               .filter(booking -> booking.getId().equals(bookingId))
                                               .findFirst()
                                               .orElseThrow(() -> new BookingNotFoundException("No booking is associated with the provided ID"));
    final Space space = this.dalSpace.getSpace(spaceName)
                                           .orElseThrow(() -> new NoSuchSpaceException("No space found with the name: " + spaceName));
    final List<Booking> bookingForTheDay = Optional.ofNullable(space.getBookings())
                                                   .orElse(Set.of())
                                                   .stream()
                                                   .filter(booking-> booking.getDate().isEqual(date))
                                                   .toList();
    final Booking tempBooking = new Booking(null,null, date, startTime, endTime, spaceName);
    final Optional<Booking> overlappingBookingOp = this.isOverlapping(tempBooking, bookingForTheDay);
    if (overlappingBookingOp.isPresent()) {
      final Booking overlappingBooking = overlappingBookingOp.get();
      throw new BookingOverlapsException("Your booking overlaps with a booking starting at " + overlappingBooking.getStartTime() + " and ending at " + overlappingBooking.getEndTime());
    }
    theBooking.update(tempBooking.getSpaceName(), tempBooking.getDate(), tempBooking.getStartTime(), tempBooking.getEndTime());
    return theBooking;
  }

  private boolean isValidBookingTime(final LocalTime startTime, final LocalTime endTime) {
    return !startTime.isAfter(endTime) &&
           !endTime.equals(LocalTime.MIDNIGHT.plusNanos(1)); // Should end at 00:00 or before
  }

  private Optional<Booking> isOverlapping(final Booking newBooking, final List<Booking> existingBookings) {
    for (final Booking booking: existingBookings) {
      if (newBooking.getStartTime().isBefore(booking.getEndTime()) &&
          newBooking.getEndTime().isAfter(booking.getStartTime())) {
        return Optional.of(booking);
      }
    }
    return Optional.empty();
  }

}
