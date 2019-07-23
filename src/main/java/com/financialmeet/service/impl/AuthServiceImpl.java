package com.financialmeet.service.impl;

import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_USER;
import static com.financialmeet.dto.shared.Status.ACTIVE_CODE;
import static com.financialmeet.util.PaginationUtil.createPageRequest;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.accounts.AuthenticationRequestDTO;
import com.financialmeet.dto.accounts.VerificationTokenDTO;
import com.financialmeet.dto.shared.Status;
import com.financialmeet.repository.accounts.AccountRepository;
import com.financialmeet.repository.accounts.VerificationTokenRepository;
import com.financialmeet.repository.shared.StatusRepostiory;
import com.financialmeet.security.auth.JwtTokenProvider;
import com.financialmeet.service.AuthService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
  private static final String EMAIL = "email";
  private static final String FRONTEND_URL = "http://localhost:4200/verify/";
  //private static final String FRONTEND_URL = "http://fyncofrontenddev.s3-website-ap-southeast-2.amazonaws.com/verify/";
  private static final String TOKEN = "token";
  private static final String ROLES  = "roles";

  private static final Logger LOGGER = Logger.getLogger( AuthServiceImpl.class.getName() );

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private EmailSenderServiceImpl emailSenderService;

  @Autowired
  private StatusRepostiory statusRepostiory;

  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @Override
  public ResponseEntity<?> signIn(@RequestBody AuthenticationRequestDTO data){
    try {
      String username = data.getUsername();
      String password = data.getPassword();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

      Optional<AccountDTO> currentUser = accountRepository.findByUsername(username);
      Map<Object,Object> model = new HashMap<>();

      if (!currentUser.isPresent()) {
        return badRequest().body("Username " + username + "not found");
      }

      List<String> roles = currentUser.get()
          .getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(toList());

      if (currentUser.get().getStatus().isEmpty() || roles.contains(ACCOUNT_ROLE_USER)) {
        if (currentUser.get().getStatus().equalsIgnoreCase(Status.UNVERIFIED_CODE)) {
          return badRequest().body("Account is not verified");
        }
      }


      String token = jwtTokenProvider.createToken(username, currentUser.get().getRoles());
      model.put(USERNAME, username);
      model.put(TOKEN, token);
      model.put(ROLES, currentUser.get().getRoles());
      return ok(model);

    } catch (Exception e){
      throw new BadCredentialsException("Invalid AccountDTO");
    }
  }

  @Override
  public ResponseEntity userSignUp(@RequestBody AccountDTO accountDTO) {

    if (checkIfEmailExist(accountDTO.getEmail())) {
      return badRequest().body("This email already exists!");
    }

    if (!accountRepository.findByUsername(accountDTO.getUsername()).isPresent()) {
      accountDTO.setPassword(encoder.encode(accountDTO.getPassword()));
      accountDTO.setRoles(singletonList(ACCOUNT_ROLE_USER));
      accountDTO.setStatus(
          statusRepostiory.findByStatusCode(Status.UNVERIFIED_CODE).get().getStatusCode());
      accountRepository.save(accountDTO);

      VerificationTokenDTO verificationToken = new VerificationTokenDTO(accountDTO);
      verificationTokenRepository.save(verificationToken);
      LOGGER.info("Token: " + verificationToken.getVerificationToken());

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(accountDTO.getEmail());
      mailMessage.setSubject("Welcome to Fynco!");
      String mailText = "To confirm your account Please click the following link: " + FRONTEND_URL + verificationToken.getVerificationToken();
      mailMessage.setText(mailText);
      //emailSenderService.sendEmail(mailMessage);
      LOGGER.info(mailText);
      LOGGER.info("EMAIL IS SENT");

      return ok(accountDTO);
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
  public Iterable<AccountDTO> getAllAgents(String firstName, String lastName, String suburb, String subType, Integer page, Integer size) {
    Pageable pageable = createPageRequest(page, size);

    if (subType.isEmpty()) {
      return accountRepository.findByFirstNameContainingAndLastNameContainingAndSuburbContainingAndRolesIn(firstName, lastName, suburb, ACCOUNT_ROLE_AGENT, pageable);
    } else {


      return accountRepository.findByFirstNameContainingAndLastNameContainingAndSuburbContainingAndSpecializationsInAndRolesIn(firstName, lastName, suburb, subType, ACCOUNT_ROLE_AGENT, pageable);
    }
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

  @Override
  public Boolean checkIfEmailExist(String email) {
    return accountRepository.findByEmailIgnoreCase(email).isPresent();
  }

  @Override
  public ResponseEntity<?> verifyToken(String token) {
    Optional<VerificationTokenDTO> verificationToken =
        verificationTokenRepository.findByVerificationToken(token);

    if (verificationToken.isPresent()) {
      Optional<AccountDTO> user =
          accountRepository.findByEmailIgnoreCase(verificationToken.get().getUser().getEmail());
      if (user.isPresent()) {
        user.get().setStatus(ACTIVE_CODE);
        accountRepository.save(user.get());
        Map<Object,Object> model = new HashMap<>();
        model.put(USERNAME, user.get().getUsername());
        model.put(EMAIL, user.get().getEmail());
        return ok(model);
      }
    }

    return badRequest().body("Cannot Verify account");
  }
}
