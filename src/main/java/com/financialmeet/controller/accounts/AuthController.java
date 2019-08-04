package com.financialmeet.controller.accounts;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.accounts.AuthenticationRequestDTO;
import com.financialmeet.service.impl.AuthServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private AuthServiceImpl authServiceImpl;

  @GetMapping("/users")
  public ResponseEntity getAllUsers(){
    return ok(authServiceImpl.getAllUsers());
  }

  @GetMapping("/agents")
  public ResponseEntity getAllAgents(
      @RequestParam(value = "firstName", required = false) String firstName,
      @RequestParam(value = "lastName", required = false) String lastName,
      @RequestParam(value = "suburb", required = false) String suburb,
      @RequestParam(value = "subType", required = false) String subType,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size
  ){
    return ok(authServiceImpl.getAllAgents(firstName, lastName, suburb, subType, page, size));
  }

  @GetMapping("/internals")
  public ResponseEntity getAllInternals(){
    return ok(authServiceImpl.getAllInternals());
  }

  @PostMapping("/signin")
  public ResponseEntity signIn(@RequestBody AuthenticationRequestDTO currentAccountDetails){
    return authServiceImpl.signIn(currentAccountDetails);
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

  @GetMapping("/roles")
  public ResponseEntity checkRole(@AuthenticationPrincipal UserDetails userDetails) {
    return ok(authServiceImpl.checkRole(userDetails));
  }

  @GetMapping("/account/{id}")
  public ResponseEntity getAgentById(@PathVariable Long id) {
    return ok(authServiceImpl.getAgent(id));
  }

  @GetMapping("/account/me")
  public ResponseEntity getAgentById(@AuthenticationPrincipal UserDetails userDetails) {
    return ok(authServiceImpl.getCurrentAccountDetails(userDetails));
  }

  @GetMapping("/verify-account")
  public ResponseEntity verifyAccount(@RequestParam(name = "token") String token){
    return authServiceImpl.verifyToken(token);

  }
}
