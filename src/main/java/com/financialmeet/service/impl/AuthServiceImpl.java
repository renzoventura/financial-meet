package com.financialmeet.service.impl;

import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_USER;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.AuthenticationRequestDTO;
import com.financialmeet.repository.AccountRepository;
import com.financialmeet.security.auth.JwtTokenProvider;
import com.financialmeet.service.AuthService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthServiceImpl implements AuthService {

  private static final String USERNAME = "username";
  private static final String TOKEN = "token";
  private static final String ROLES  = "roles";

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public Map signIn(@RequestBody AuthenticationRequestDTO data){
    try {
      String username = data.getUsername();
      String password = data.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

      Optional<AccountDTO> currentUser = accountRepository.findByUsername(username);

      if(!currentUser.isPresent())
        throw new UsernameNotFoundException("Username " + username + "not found");

      String token = jwtTokenProvider.createToken(username, currentUser.get().getRoles());

      Map<Object,Object> model = new HashMap<>();
      model.put(USERNAME, username);
      model.put(TOKEN, token);
      model.put(ROLES, currentUser.get().getRoles());
      return model;

    } catch (Exception e ){
      throw new BadCredentialsException("Invalid AccountDTO");
    }
  }

  @Override
  public ResponseEntity userSignUp(@RequestBody AccountDTO accountDTO) {
    if (!accountRepository.findByUsername(accountDTO.getUsername()).isPresent()) {
      accountDTO.setPassword(encoder.encode(accountDTO.getPassword()));
      accountDTO.setRoles(singletonList(ACCOUNT_ROLE_USER));
      return ok(accountRepository.save(accountDTO));
    }
    throw new IllegalArgumentException("The username is already in use");
  }

  public ResponseEntity agentSignUp(@RequestBody AccountDTO accountDTO) {
    if (!accountRepository.findByUsername(accountDTO.getUsername()).isPresent()) {
      accountDTO.setPassword(encoder.encode(accountDTO.getPassword()));
      accountDTO.setRoles(singletonList(ACCOUNT_ROLE_AGENT));
      return ok(accountRepository.save(accountDTO));
    }
    throw new IllegalArgumentException("The username is already in use");
  }

  @Override
  public ResponseEntity internalSignUp(@RequestBody AccountDTO accountDTO) {
    if (!accountRepository.findByUsername(accountDTO.getUsername()).isPresent()) {
      accountDTO.setPassword(encoder.encode(accountDTO.getPassword()));
      accountDTO.setRoles(singletonList(ACCOUNT_ROLE_INTERNAL));
      return ok(accountRepository.save(accountDTO));
    }
    throw new IllegalArgumentException("The username is already in use");
  }

  @Override
  public Map<Object, Object> getCurrentUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
    Map<Object, Object> account = new HashMap<>();
    account.put(USERNAME, userDetails.getUsername());
    account.put(
        ROLES,
        userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toList()));
    return account;

  }

  @Override
  public Map<Object, Object> getCurrentUserRoles(UserDetails userDetails) {
    Map<Object, Object> account = new HashMap<>();
    account.put(
        ROLES,
        userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toList()));
    return account;
  }

  @Override
  public ResponseEntity checkTokenVadility(@AuthenticationPrincipal String token) {
    return ok((token));
  }

  @Override
  public Iterable checkRole(UserDetails userDetails) {
    return userDetails
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(toList());
  }

  @Override
  public Iterable<AccountDTO> getAllUsers() {
    return accountRepository.findByRolesIn(ACCOUNT_ROLE_USER);
  }

  @Override
  public Iterable<AccountDTO> getAllAgents() {
    return accountRepository.findByRolesIn(ACCOUNT_ROLE_AGENT);
  }

  @Override
  public Iterable<AccountDTO> getAllInternals() {
    return accountRepository.findByRolesIn(ACCOUNT_ROLE_INTERNAL);
  }

  @Override
  public ResponseEntity<?> getAgent(Long accountId) {
    Optional<AccountDTO> account = accountRepository.findById(accountId);
    if (account.isPresent()) {
      AccountDTO accountFound = account.get();
      if (accountFound.getRoles().contains(ACCOUNT_ROLE_AGENT)) {
        return ok(accountFound);
      }
    }
      return badRequest().body("Account cannot be found");
  }


  @Override
  public ResponseEntity getCurrentAccountDetails(@AuthenticationPrincipal UserDetails userDetails) {
    Optional<AccountDTO> currentAccount = accountRepository.findByUsername(userDetails.getUsername());
    if (currentAccount.isPresent()) {
      return ok(currentAccount.get());
    }
    return badRequest().body("Account does not exist");


  }
}
