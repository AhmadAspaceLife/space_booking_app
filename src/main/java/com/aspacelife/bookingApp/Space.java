package com.aspacelife.bookingApp;

import io.vertx.core.json.JsonObject;

/**
 * @author AHMAD BUBA
 * Date:3/17/25
 * Time:11:41
 */

public class Space {
  private final String id;
  private final String name;
  private boolean available;

  public Space(final String id, final String name, final boolean available) {
    this.id = id;
    this.name = name;
    this.available = available;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailable(final boolean available) {
    this.available = available;
  }

  public JsonObject toJson() {
    return new JsonObject()
             .put("id", this.id)
             .put("name", this.name)
             .put("available", this.available);
  }

  @Override
  public boolean equals(final Object other) {
    return other instanceof Space
           && ((Space) other).getName()
                                  .equals(this.getName());
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

}
