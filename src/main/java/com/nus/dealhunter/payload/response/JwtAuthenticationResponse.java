package com.nus.dealhunter.payload.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

  private String accessToken;

  private String tokenType = "Bearer";

  private String username;

  private Integer isAdmin;

  private Long userId;

  private String email;

  public JwtAuthenticationResponse(String accessToken, Long id, String username, String email, Integer isAdmin) {
    this.accessToken = accessToken;
    this.username = username;
    this.isAdmin = isAdmin;
    this.userId = id;
    this.email = email;
  }
}
