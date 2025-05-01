package com.aspacelife.spaceBookingApp.booking.core.spaceBookingManagement.rest.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author AHMAD BUBA
 * Date:4/30/25
 * Time:17:00
 */

public record UpdateBookingDto(
  @NotBlank(message = "Space name must be present and not empty") String spaceName,
  @NotNull(message = "Date must be provided") LocalDate date,
  @NotNull(message = "Start time must be provided") LocalTime startTime,
  @NotNull(message = "End time must be provided")LocalTime endTime
) {}
