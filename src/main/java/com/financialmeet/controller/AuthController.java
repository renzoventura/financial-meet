package com.financialmeet.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.model.Account;
import com.financialmeet.model.AuthenticationRequest;
import com.financialmeet.service.AuthService;
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
  private AuthService authService;

  @PostMapping("/signin")
  public ResponseEntity signIn(@RequestBody AuthenticationRequest data){
    return ok(authService.signIn(data));
  }

  @PostMapping("/signup/user")
  public ResponseEntity userSignUp(@RequestBody Account account) {
    return authService.userSignUp(account);
  }

  @PostMapping("/signup/agent")
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity signupAdmin(@RequestBody Account account) {
    return authService.agentSignUp(account);
  }

  @PostMapping("/signup/internal")
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity internalSignUp(@RequestBody Account account) {
    return authService.internalSignUp(account);
  }

  @GetMapping("/me")
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity getCurrentUserDetails(@AuthenticationPrincipal UserDetails userDetails){
    return ok(authService.getCurrentUserDetails(userDetails));
  }

}

