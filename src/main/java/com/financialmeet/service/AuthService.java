package com.financialmeet.service;

import com.financialmeet.model.Account;
import com.financialmeet.model.AuthenticationRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

  Map signIn(AuthenticationRequest data);

  ResponseEntity userSignUp(Account account);

  ResponseEntity agentSignUp(Account account);

  ResponseEntity internalSignUp(Account account);

  ResponseEntity getCurrentUserDetails(UserDetails userDetails);

}
