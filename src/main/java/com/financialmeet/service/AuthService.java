package com.financialmeet.service;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.accounts.AuthenticationRequestDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

  ResponseEntity<?> signIn(AuthenticationRequestDTO data);

  ResponseEntity userSignUp(AccountDTO accountDTO);

  ResponseEntity agentSignUp(AccountDTO accountDTO);

  ResponseEntity internalSignUp(AccountDTO accountDTO);

  Map<Object, Object> getCurrentUserDetails(UserDetails userDetails);

  Map<Object, Object> getCurrentUserRoles(UserDetails userDetails);

  ResponseEntity checkTokenVadility(String token);

  Iterable checkRole(UserDetails userDetails);

  Iterable<AccountDTO> getAllUsers();

  Iterable<AccountDTO> getAllAgents(String firstName, String lastName, String suburb, String subType, Integer page, Integer size);

  Iterable<AccountDTO> getAllInternals();

  ResponseEntity getAgent(Long accountId);

  ResponseEntity getCurrentAccountDetails(UserDetails userDetails);

  Boolean checkIfEmailExist(String email);

  AccountDTO verifyToken(String token);

}
