package com.aspacelife.bookingApp.model.dao;

import com.aspacelife.bookingApp.model.dal.DalUser;
import io.vertx.core.impl.ConcurrentHashSet;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class DaoUser implements DalUser {
  private final Set<String> users =  new ConcurrentHashSet<>();

  @Override
  public Set<String> getUsers() {
    return this.users;
  }
}
