package com.financialmeet.controller;

import static org.springframework.http.ResponseEntity.badRequest;
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
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private AuthServiceImpl authServiceImpl;

  @PostMapping("/signin")
  public ResponseEntity signIn(@RequestBody AuthenticationRequestDTO currentAccountDetails){
    return ok(authServiceImpl.signIn(currentAccountDetails));
  }

  @PostMapping("/signup/user")
  public ResponseEntity userSignUp(@RequestBody AccountDTO accountDTO) {
    return authServiceImpl.userSignUp(accountDTO);
  }

  @PostMapping("/signup/agent")
  public ResponseEntity signupAdmin(@RequestBody AccountDTO accountDTO) {
    return authServiceImpl.agentSignUp(accountDTO);
  }

  @PostMapping("/signup/internal")
  public ResponseEntity internalSignUp(@RequestBody AccountDTO accountDTO) {
    return authServiceImpl.internalSignUp(accountDTO);
  }

  @GetMapping("/me")
  public ResponseEntity getCurrentUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
    return ok(authServiceImpl.getCurrentUserDetails(userDetails));
  }

  @GetMapping("/checkrole")
  public ResponseEntity checkTokenVadality(@AuthenticationPrincipal UserDetails userDetails) {
    try {
      return ok(authServiceImpl.getCurrentUserRoles(userDetails));
    } catch (Exception e){
      return badRequest().body("Session is invalid");
    }
  }

  @GetMapping("/check/user")
  public ResponseEntity checkIfUser(@AuthenticationPrincipal UserDetails userDetails) {
    if (authServiceImpl.checkIfUser(userDetails)) {
      return ok(true);
    }
    return badRequest().body(false);
  }

  @GetMapping("/check/agent")
  public ResponseEntity checkIfAgent(@AuthenticationPrincipal UserDetails userDetails) {
    if (authServiceImpl.checkIfAgent(userDetails)) {
      return ok(true);
    }
    return badRequest().body(false);
  }

  @GetMapping("/check/Internal")
  public ResponseEntity checkIfInternal(@AuthenticationPrincipal UserDetails userDetails) {
    if (authServiceImpl.checkIfInternal(userDetails)) {
      return ok(true);
    }
    return badRequest().body(false);
  }
}
