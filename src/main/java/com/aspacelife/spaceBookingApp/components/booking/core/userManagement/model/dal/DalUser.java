package com.aspacelife.spaceBookingApp.components.booking.core.userManagement.model.dal;

import java.util.Set;

import com.aspacelife.spaceBookingApp.components.booking.core.userManagement.exception.UserExistsException;
import com.aspacelife.spaceBookingApp.components.booking.core.userManagement.model.dto.User;

import lombok.NonNull;

public interface DalUser {

  Set<User> getUsers();
  User addUser(@NonNull final String email, @NonNull final String name) throws UserExistsException;
  boolean userExists(String userEmail);

}
