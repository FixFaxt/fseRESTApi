package main.fseRESTApi.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRoles {
  ADMIN,
  USER;

  @JsonCreator
  public static UserRoles fromValue(String value) {
    for (UserRoles role : UserRoles.values()) {
      if (role.name().equalsIgnoreCase(value)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Invalid role: " + value);
  }

  @JsonValue
  public String toValue() {
    return this.name();
  }
}
