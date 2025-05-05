package com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.BookingNotFoundException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.BookingOverlapsException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.InvalidBookingTime;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.exception.NoSuchSpaceException;
import com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.model.dto.Booking;
import com.aspacelife.spaceBookingApp.components.booking.core.userManagement.exception.NoSuchUserException;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:15:39
 */

public interface DalBooking {
  Booking createBooking(String userEmail, String username, String spaceName, LocalDate date, LocalTime startTime, LocalTime endTime) throws NoSuchSpaceException, InvalidBookingTime, BookingOverlapsException, NoSuchUserException;
  Optional<Booking> getBooking(String bookingId);
  Set<Booking> getAllBookings();
  Set<Booking> getAllSpaceBookings(String spaceName);
  Set<Booking> getAllUserBookings(String userEmail);
  Booking updateBooking(String bookingId, String spaceName, LocalDate date, LocalTime startTime, LocalTime endTime) throws NoSuchSpaceException, InvalidBookingTime, BookingOverlapsException, BookingNotFoundException;
}
