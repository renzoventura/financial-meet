package com.financialmeet;

import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_USER;
import static java.time.LocalDate.ofEpochDay;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import com.financialmeet.dto.applications.ApplicationStatusDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;
import com.financialmeet.repository.accounts.AccountRepository;
import com.financialmeet.repository.applications.ApplicationRepository;
import com.financialmeet.repository.applications.ApplicationStatusRepository;
import com.financialmeet.repository.applications.ApplicationTypeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired private AccountRepository accountRepository;

  @Autowired private ApplicationRepository applicationRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private ApplicationTypeRepository applicationTypeRepository;

  @Autowired private ApplicationStatusRepository applicationStatusRepository;

  @Override
  public void run(String... args) {
    ArrayList<AccountDTO> accountDTOS = new ArrayList<>(2);

    AccountDTO user = new AccountDTO();
    user.setUsername("user");
    user.setFirstName("Kobe");
    user.setLastName("Bryant");
    user.setPassword(this.passwordEncoder.encode("password"));
    user.setPhoneNumber("027676898");
    user.setMobile("027676898");
    user.setOccupation("Student");
    user.setSuburb("Auckland");
    user.setEmail("renzoventura@gmail.com");
    user.setAnnualIncome("$10,000-$50,000");
    user.setCurrentLoan("$10,000-$50,000");
    user.setRoles(Collections.singletonList(ACCOUNT_ROLE_USER));
    accountRepository.save(user);

    ApplicationStatusDTO status1 = new ApplicationStatusDTO();
    status1.setApplicationStatusCode("CREATED");
    status1.setApplicationStatusTitle("CREATED_TITLE");
    applicationStatusRepository.save(status1);
    ApplicationStatusDTO status2 = new ApplicationStatusDTO();
    status2.setApplicationStatusCode("ASSIGNED");
    status2.setApplicationStatusTitle("ASSIGNED_TITLE");
    applicationStatusRepository.save(status2);
    ApplicationStatusDTO status3 = new ApplicationStatusDTO();
    status3.setApplicationStatusCode("DONE");
    status3.setApplicationStatusTitle("DONE_TITLE");
    applicationStatusRepository.save(status3);
    ApplicationStatusDTO status4 = new ApplicationStatusDTO();
    status4.setApplicationStatusCode("INSU_1");
    status4.setApplicationStatusTitle("INSURANCE_1");
    applicationStatusRepository.save(status4);
    ApplicationStatusDTO status5 = new ApplicationStatusDTO();
    status5.setApplicationStatusCode("INSU_2");
    status5.setApplicationStatusTitle("INSURANCE_2");
    applicationStatusRepository.save(status5);
    ApplicationStatusDTO status6 = new ApplicationStatusDTO();
    status6.setApplicationStatusCode("INSU_3");
    status6.setApplicationStatusTitle("INSURANCE_3");
    applicationStatusRepository.save(status6);

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
    agent.setFirstName("Kazuya");
    agent.setLastName("Mishima");
    agent.setPassword(this.passwordEncoder.encode("password"));
    agent.setRoles(Collections.singletonList(ACCOUNT_ROLE_AGENT));
    accountRepository.save(agent);

    AccountDTO internal = new AccountDTO();
    internal.setUsername("internal");
    internal.setFirstName("Internal");
    internal.setLastName("Account");
    internal.setPassword(this.passwordEncoder.encode("password"));
    internal.setRoles(Collections.singletonList(ACCOUNT_ROLE_INTERNAL));
    accountRepository.save(internal);

    AccountDTO user2 = new AccountDTO();
    user2.setUsername("user2");
    user2.setFirstName("Bob");
    user2.setLastName("Phoenix");
    user2.setPassword(this.passwordEncoder.encode("password"));
    user2.setRoles(Collections.singletonList(ACCOUNT_ROLE_USER));
    accountRepository.save(user2);

    ApplicationDTO application;

    for (int i = 0; i < 20; i++) {
      application = new ApplicationDTO();
      application.setOwner(user);
      application.setTitle(String.format("%d Application", i));
      application.setDescription(String.format("%d Application Description", i));
      long minDay = LocalDate.of(2019, 1, 1).toEpochDay();
      long randomDay = ThreadLocalRandom.current().nextLong(minDay, LocalDate.now().toEpochDay());
      application.setDateCreated(ofEpochDay(randomDay));
      application.setApplicationType(mortgage.getApplicationTypeCode());
      application.setStatus("CREATED");
      if (i % 2 == 0) {
        application.setAgent(agent);
      }
      applicationRepository.save(application);
    }

    // create agents
    for (int i = 0; i < 20; i++) {
      agent = new AccountDTO();
      agent.setUsername(String.format("agent%d", i));
      agent.setFirstName(String.format("First Name:%d", i));
      agent.setLastName(String.format("Last Name:%d", i));
      if (i % 2 == 0) {
        agent.setSuburb("Long Bay");
      } else {
        agent.setSuburb("Albany");
      }
      agent.setEmail(String.format("agent@%d.com", i));
      agent.setPassword(this.passwordEncoder.encode("password"));
      agent.setRoles(Collections.singletonList(ACCOUNT_ROLE_AGENT));
      accountRepository.save(agent);
    }
  }
}
