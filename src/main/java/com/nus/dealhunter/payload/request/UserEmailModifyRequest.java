package com.nus.dealhunter.payload.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserEmailModifyRequest {

  @Size(max = 100)
  private String email;

}
