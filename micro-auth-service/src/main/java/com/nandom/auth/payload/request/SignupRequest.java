package com.nandom.auth.payload.request;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;


@Data
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 3, max = 40)
  private String password;


}
