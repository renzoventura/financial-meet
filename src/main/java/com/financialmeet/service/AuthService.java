package com.financialmeet.service;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.AuthenticationRequestDTO;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

  Map signIn(AuthenticationRequestDTO data);

  ResponseEntity userSignUp(AccountDTO accountDTO);

  ResponseEntity agentSignUp(AccountDTO accountDTO);

  ResponseEntity internalSignUp(AccountDTO accountDTO);

  Map<Object, Object> getCurrentUserDetails(UserDetails userDetails);

  Map<Object, Object> getCurrentUserRoles(UserDetails userDetails);

  ResponseEntity checkTokenVadility(String token);

  Iterable checkRole(UserDetails userDetails);

  Iterable<AccountDTO> getAllUsers();

  Iterable<AccountDTO> getAllAgents(String firstName, String lastName, String suburb, Integer page, Integer size);

  Iterable<AccountDTO> getAllInternals();

  ResponseEntity getAgent(Long accountId);

  ResponseEntity getCurrentAccountDetails(UserDetails userDetails);

}
