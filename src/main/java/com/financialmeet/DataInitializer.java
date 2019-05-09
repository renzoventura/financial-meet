package com.financialmeet;

import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.AccountDTO.ACCOUNT_ROLE_USER;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import com.financialmeet.dto.ApplicationTypeDTO;
import com.financialmeet.dto.StatusDTO;
import com.financialmeet.repository.AccountRepository;
import com.financialmeet.repository.ApplicationRepository;
import com.financialmeet.repository.ApplicationTypeRepository;
import com.financialmeet.repository.StatusRepository;
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

  @Autowired
  private ApplicationTypeRepository applicationTypeRepository;

  @Autowired
  private StatusRepository statusRepository;

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

    ArrayList<String> statuses = new ArrayList<String>() {
      {
        add("ACTIVE");
        add("DEACTIVED");
        add("LOCKED");
        add("DELETED");
        add("ARCHIVED");
        add("READY");
        add("DELIVERED");
      }
    };

    StatusDTO status1 = new StatusDTO();
    status1.setStatusCode("TEST");
    statusRepository.save(status1);

    ApplicationTypeDTO mortgage = new ApplicationTypeDTO();
    mortgage.setApplicationTypeTitle("MORTGAGE");
    applicationTypeRepository.save(mortgage);
    mortgage.getStatuses().add(status1);

    for(String statusString : statuses) {
      StatusDTO status = new StatusDTO();
      status.setStatusCode(statusString);
      statusRepository.save(status);
      mortgage.getStatuses().add(status);
    }

    AccountDTO agent = new AccountDTO();
    agent.setUsername("agent");
    agent.setPassword(this.passwordEncoder.encode("password"));
    agent.setRoles(Collections.singletonList(ACCOUNT_ROLE_AGENT));
    accountRepository.save(agent);

    AccountDTO internal = new AccountDTO();
    internal.setUsername("internal");
    internal.setPassword(this.passwordEncoder.encode("password"));
    internal.setRoles(Collections.singletonList(ACCOUNT_ROLE_INTERNAL));
    accountRepository.save(internal);

    AccountDTO user2 = new AccountDTO();
    user2.setUsername("user2");
    user2.setPassword(this.passwordEncoder.encode("password"));
    user2.setRoles(Collections.singletonList(ACCOUNT_ROLE_USER));
    accountRepository.save(user2);

    ApplicationDTO application = new ApplicationDTO();
    application.setOwner(user);
    application.setTitle("I need help with my financial needs!");

    application.setAnnualIncome(income);
    application.setDescription(applicationDescription);
    application.setNumberOfChildren(3);
    application.setRates(rate);
    application.setExistingMortgage(true);
    application.setApplicationTypeDTO(mortgage);
    applicationRepository.save(application);

    application = new ApplicationDTO();
    application.setOwner(user);
    application.setAgent(agent);
    application.setTitle("This application has an agent");
    applicationRepository.save(application);

  }

}
