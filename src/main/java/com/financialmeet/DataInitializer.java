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

    AccountDTO user = new AccountDTO();
    user.setUsername("user");
    user.setPassword(this.passwordEncoder.encode("password"));
    user.setRoles(Collections.singletonList(ACCOUNT_ROLE_USER));
    accountRepository.save(user);

    StatusDTO status1 = new StatusDTO();
    status1.setStatusCode("MORT_1");
    status1.setStatusTitle("MORTGAGE_1");
    statusRepository.save(status1);
    StatusDTO status2 = new StatusDTO();
    status2.setStatusCode("MORT_2");
    status2.setStatusTitle("MORTGAGE_2");
    statusRepository.save(status2);
    StatusDTO status3 = new StatusDTO();
    status3.setStatusCode("MORT_3");
    status3.setStatusTitle("MORTGAGE_3");
    statusRepository.save(status3);
    StatusDTO status4 = new StatusDTO();
    status4.setStatusCode("INSU_1");
    status4.setStatusTitle("INSURANCE_1");
    statusRepository.save(status4);
    StatusDTO status5 = new StatusDTO();
    status5.setStatusCode("INSU_2");
    status5.setStatusTitle("INSURANCE_2");
    statusRepository.save(status5);
    StatusDTO status6 = new StatusDTO();
    status6.setStatusCode("INSU_3");
    status6.setStatusTitle("INSURANCE_3");
    statusRepository.save(status6);


    ApplicationTypeDTO mortgage = new ApplicationTypeDTO();
    mortgage.setApplicationTypeCode("MORT");
    mortgage.setApplicationTypeTitle("MORTGAGE");
    mortgage.getStatuses().add(status1);
    mortgage.getStatuses().add(status2);
    mortgage.getStatuses().add(status3);
    applicationTypeRepository.save(mortgage);

    ApplicationTypeDTO insurance = new ApplicationTypeDTO();
    insurance.setApplicationTypeCode("INSU");
    insurance.setApplicationTypeTitle("INSURANCE");
    insurance.getStatuses().add(status4);
    insurance.getStatuses().add(status5);
    insurance.getStatuses().add(status6);
    applicationTypeRepository.save(insurance);

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

    application.setDescription(applicationDescription);
    application.setApplicationType(mortgage.getApplicationTypeCode());
    applicationRepository.save(application);

    application = new ApplicationDTO();
    application.setOwner(user);
    application.setAgent(agent);
    application.setTitle("This application has an agent");
    applicationRepository.save(application);

  }

}
