package main.fseRESTApi.api.model;

import java.util.UUID;

public class ValidatePasswordDto {
  private UUID userId;
  private String rawPassword;

  private ValidatePasswordDto(UUID userId, String rawPassword) {
    this.userId = userId;
    this.rawPassword = rawPassword;
  }

  public UUID getUserId() {
    return this.userId;
  }

  public String getRawPassword() {
    return this.rawPassword;
  }
}