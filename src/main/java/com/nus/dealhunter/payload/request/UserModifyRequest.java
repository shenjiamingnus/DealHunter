package com.nus.dealhunter.payload.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModifyRequest {

  @Size(max = 100)
  private String password;


}
