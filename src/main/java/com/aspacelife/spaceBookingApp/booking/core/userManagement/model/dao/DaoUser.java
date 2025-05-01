package com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.aspacelife.spaceBookingApp.booking.core.userManagement.exception.UserExistsException;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dal.DalUser;
import com.aspacelife.spaceBookingApp.booking.core.userManagement.model.dto.User;

import io.vertx.core.impl.ConcurrentHashSet;
import lombok.NonNull;

@Repository
public class DaoUser implements DalUser {
  private final Set<User> users =  new ConcurrentHashSet<>();

  @Override
  public Set<User> getUsers() {
    return this.users;
  }

  @Override
  public User addUser(final @NonNull String email, final @NonNull String name) throws UserExistsException {
    final User newUser = new User(email, name);

    final boolean added = this.users.add(newUser);

    if (!added) {
      throw new UserExistsException("User with " + email + " all ready exists");
    }

    return newUser;
  }

  @Override
  public boolean userExists(final String userEmail) {
    return this.users.contains(new User(userEmail, "dummy"));
  }

}
