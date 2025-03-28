package com.aspacelife.bookingApp.model.dto;

import java.time.LocalDateTime;

import com.aspacelife.bookingApp.BookingStatus;

/**
 * @author AHMAD BUBA
 * Date:3/17/25
 * Time:11:43
 */

public class Booking {
  private String id;
  private String user;
  private Space space;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private BookingStatus status;

  public Booking(String id, String user, Space space, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status) {
    this.id = id;
    this.user = user;
    this.space = space;
    this.startTime = startTime;
    this.endTime = endTime;
    this.status = status;
  }

  public boolean overlapsWith(Booking other) {
    return this.space.equals(other.space) &&
           this.startTime.isBefore(other.endTime) &&
           this.endTime.isAfter(other.startTime);
  }


}
