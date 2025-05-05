package com.aspacelife.spaceBookingApp.components.booking.core.spaceBookingManagement.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author AHMAD BUBA
 * Date:4/15/25
 * Time:12:45
 */

public record CreateSpaceDto(@NotBlank(message = "Name must be present") String name, @NotNull boolean available) {
}
