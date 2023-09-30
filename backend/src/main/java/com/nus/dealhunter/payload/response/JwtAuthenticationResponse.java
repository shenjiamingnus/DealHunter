package com.nus.dealhunter.payload.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

  private String accessToken;

  private String tokenType = "Bearer";

  private String username;

  private Integer gender;

  private String avatar;

  public JwtAuthenticationResponse(String accessToken, String username, Integer gender, String avatar) {
    this.accessToken = accessToken;
    this.username = username;
    this.gender = gender;
    this.avatar = avatar;
  }
}
