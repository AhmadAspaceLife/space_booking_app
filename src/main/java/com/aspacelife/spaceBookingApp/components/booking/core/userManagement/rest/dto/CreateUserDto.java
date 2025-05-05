package com.aspacelife.spaceBookingApp.components.booking.core.userManagement.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author AHMAD BUBA
 * Date:4/14/25
 * Time:17:04
 */

public record CreateUserDto(
  @NotBlank @Email(message = "Email must be valid") String email,
  @NotBlank(message = "name cannot be blank") String name
) {}
