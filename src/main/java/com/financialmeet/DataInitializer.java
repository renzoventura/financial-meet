package com.financialmeet;

import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_USER;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import com.financialmeet.repository.AccountRepository;
import com.financialmeet.repository.ApplicationRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ApplicationRepository applicationRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    ArrayList<AccountDTO> accountDTOS = new ArrayList<>(2);

    String applicationDescription = "This is my application for my financial needs";
    BigDecimal income = new BigDecimal(5000000.00);
    BigDecimal rate = new BigDecimal(70.00);

    AccountDTO user = new AccountDTO();
    user.setUsername("user");
    user.setPassword(this.passwordEncoder.encode("password"));
    user.setRoles(Collections.singletonList(ACCOUNT_ROLE_USER));
    accountRepository.save(user);

    ApplicationDTO application = new ApplicationDTO();
    application.setOwner(user);
    application.setTitle("I need help with my financial needs!");

    application.setAnnualIncome(income);
    application.setDescription(applicationDescription);
    application.setNumberOfChildren(3);
    application.setRates(rate);
    application.setExistingMortgage(true);
    applicationRepository.save(application);

    user = new AccountDTO();
    user.setUsername("agent");
    user.setPassword(this.passwordEncoder.encode("password"));
    user.setRoles(Collections.singletonList(ACCOUNT_ROLE_AGENT));
    accountRepository.save(user);

    user = new AccountDTO();
    user.setUsername("internal");
    user.setPassword(this.passwordEncoder.encode("password"));
    user.setRoles(Collections.singletonList(ACCOUNT_ROLE_INTERNAL));
    accountRepository.save(user);

  }

}
