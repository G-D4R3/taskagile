package com.taskagile.web.apis;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.application.model.user.EmailAddressExistsException;
import com.taskagile.domain.application.model.user.RegistrationException;
import com.taskagile.domain.application.model.user.UsernameExistsException;
import com.taskagile.web.payload.RegistrationPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RegistrationApiController {
  private UserService service;

  public RegistrationApiController(UserService service){
    this.service = service;
  }

  @PostMapping("/api/registrations")
  public ResponseEntity<ApiResult> register(
    // 스프링MVC는 @Valid 어노테이션이 존재할 때 register 메소드에 데이터를 전달하기 전에
    // RegistrationPayload의 데이터 검증을 수행해서 데이터가 유효한지 확인한다.
    @Valid @RequestBody RegistrationPayload payload) {
    try {
      service.register(payload.toCommand());
      return Result.created();
    }
    catch(RegistrationException e){
      String errorMessage = "Registration failed";
      if(e instanceof UsernameExistsException) {
        errorMessage = "Username already exists";
      } else if(e instanceof EmailAddressExistsException) {
        errorMessage = "Email address already exists";
      }
      return Result.failure(errorMessage);
    }
  }
}
