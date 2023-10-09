package com.nus.dealhunter.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdminCreateRequest {

  @NotBlank
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(max = 50)
  private String password;

  @NotBlank
  @Size(max = 50)
  private String email;


}
