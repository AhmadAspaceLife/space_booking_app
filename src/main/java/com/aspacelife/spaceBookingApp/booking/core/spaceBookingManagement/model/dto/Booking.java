package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * @author AHMAD BUBA
 * Date:3/17/25
 * Time:11:43
 */

@Getter
public class Booking {
  private String id;
  @Setter
  private String userEmail;
  @Setter
  private String username;
  @Setter
  private LocalDate date;
  @Setter
  private LocalTime startTime;
  @Setter
  private LocalTime endTime;
  @Setter
  private String spaceName;
  @Setter
  private boolean checkedIn;

  public Booking(final String userEmail, final String username, final LocalDate date, final LocalTime startTime, final LocalTime endTime, final String spaceName) {
    this.id = UUID.randomUUID().toString();
    this.userEmail = userEmail;
    this.username = username;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.spaceName = spaceName;
    this.checkedIn = false;
  }

  public void update(final String spaceName, final LocalDate date, final LocalTime startTime, final LocalTime endTime) {
    this.spaceName = spaceName;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
  }

}
