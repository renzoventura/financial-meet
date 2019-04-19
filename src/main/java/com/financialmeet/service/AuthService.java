package com.financialmeet.service;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.AuthenticationRequestDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

  Map signIn(AuthenticationRequestDTO data);

  ResponseEntity userSignUp(AccountDTO accountDTO);

  ResponseEntity agentSignUp(AccountDTO accountDTO);

  ResponseEntity internalSignUp(AccountDTO accountDTO);

  ResponseEntity<?> getCurrentUserDetails(UserDetails userDetails);

  ResponseEntity checkTokenVadality(String token);

}
