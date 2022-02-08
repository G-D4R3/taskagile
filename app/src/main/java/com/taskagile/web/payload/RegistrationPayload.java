package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.RegistrationCommand;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// size 어노테이션은 null은 유효한 값을 간주하기 때문에 notnull 어노테이션을 추가한다.
// 각 필드에 적용한 제약조건을 다루기 위해 RegistrationPayloadTests에 test method를 추가한다.

public class RegistrationPayload {
  @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
  @NotNull
  private String username;

  @Email(message = "Email address should be valid")
  @Size(max = 100, message = "Email address must not be more than 100 characters")
  @NotNull
  private String emailAddress;

  @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
  @NotNull
  private String password;

  public RegistrationCommand toCommand() {
    return new RegistrationCommand(this.username, this.emailAddress, this.password);
  }

  public String getUsername() {
    return username;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
