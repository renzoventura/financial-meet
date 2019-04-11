package com.financialmeet.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.AuthenticationRequestDTO;
import com.financialmeet.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private AuthServiceImpl authServiceImpl;

  @PostMapping("/signin")
  public ResponseEntity signIn(@RequestBody AuthenticationRequestDTO data){
    return ok(authServiceImpl.signIn(data));
  }

  @PostMapping("/signup/user")
  public ResponseEntity userSignUp(@RequestBody AccountDTO accountDTO) {
    return authServiceImpl.userSignUp(accountDTO);
  }

  @PostMapping("/signup/agent")
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity signupAdmin(@RequestBody AccountDTO accountDTO) {
    return authServiceImpl.agentSignUp(accountDTO);
  }

  @PostMapping("/signup/internal")
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity internalSignUp(@RequestBody AccountDTO accountDTO) {
    return authServiceImpl.internalSignUp(accountDTO);
  }

  @GetMapping("/me")
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity getCurrentUserDetails(@AuthenticationPrincipal UserDetails userDetails){
    return ok(authServiceImpl.getCurrentUserDetails(userDetails));
  }

}
