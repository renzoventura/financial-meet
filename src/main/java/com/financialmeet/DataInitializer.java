package com.financialmeet;

import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_USER;
import static java.time.LocalDate.ofEpochDay;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import com.financialmeet.dto.applications.ApplicationStatusDTO;
import com.financialmeet.dto.applications.ApplicationSubTypeDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;
import com.financialmeet.repository.accounts.AccountRepository;
import com.financialmeet.repository.applications.ApplicationRepository;
import com.financialmeet.repository.applications.ApplicationStatusRepository;
import com.financialmeet.repository.applications.ApplicationSubTypeRepository;
import com.financialmeet.repository.applications.ApplicationTypeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired private AccountRepository accountRepository;

  @Autowired private ApplicationRepository applicationRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private ApplicationTypeRepository applicationTypeRepository;

  @Autowired private ApplicationStatusRepository applicationStatusRepository;

  @Autowired
  private ApplicationSubTypeRepository applicationSubTypeRepository;

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
    status4.setApplicationStatusCode("CREATED_INS");
    status4.setApplicationStatusTitle("INSURANCE_1");
    applicationStatusRepository.save(status4);
    ApplicationStatusDTO status5 = new ApplicationStatusDTO();
    status5.setApplicationStatusCode("ASSIGNED_INS");
    status5.setApplicationStatusTitle("INSURANCE_2");
    applicationStatusRepository.save(status5);
    ApplicationStatusDTO status6 = new ApplicationStatusDTO();
    status6.setApplicationStatusCode("DONE");
    status6.setApplicationStatusTitle("DONE_TITLE");
    applicationStatusRepository.save(status6);

    ApplicationTypeDTO mortgage = new ApplicationTypeDTO();
    mortgage.setApplicationTypeCode("MORTGAGE");
    mortgage.setApplicationTypeTitle("Mortgage");
    mortgage.getStatuses().add(status1);
    mortgage.getStatuses().add(status2);
    mortgage.getStatuses().add(status3);
    applicationTypeRepository.save(mortgage);

    ApplicationSubTypeDTO residentialLoan = new ApplicationSubTypeDTO();
    residentialLoan.setParentType(mortgage);
    residentialLoan.setApplicationSubTypeTitle("Residential Loan");
    residentialLoan.setApplicationSubTypeCode("RESIDENTIAL");
    applicationSubTypeRepository.save(residentialLoan);

    ApplicationSubTypeDTO commercialLoan = new ApplicationSubTypeDTO();
    commercialLoan.setParentType(mortgage);
    commercialLoan.setApplicationSubTypeTitle("Commercial Loan");
    commercialLoan.setApplicationSubTypeCode("COMMERCIAL");
    applicationSubTypeRepository.save(commercialLoan);

    ApplicationSubTypeDTO constructionLoan = new ApplicationSubTypeDTO();
    constructionLoan.setParentType(mortgage);
    constructionLoan.setApplicationSubTypeTitle("Construction Loan");
    constructionLoan.setApplicationSubTypeCode("CONSTRUCTION");
    applicationSubTypeRepository.save(constructionLoan);

    ApplicationSubTypeDTO businessLoan = new ApplicationSubTypeDTO();
    businessLoan.setParentType(mortgage);
    businessLoan.setApplicationSubTypeTitle("Business Loan");
    businessLoan.setApplicationSubTypeCode("BUSINESS");
    applicationSubTypeRepository.save(businessLoan);

    ApplicationTypeDTO insurance = new ApplicationTypeDTO();
    insurance.setApplicationTypeCode("INSURANCE");
    insurance.setApplicationTypeTitle("Insurance");
    insurance.getStatuses().add(status4);
    insurance.getStatuses().add(status5);
    insurance.getStatuses().add(status6);
    applicationTypeRepository.save(insurance);


    ApplicationSubTypeDTO healthInsurance = new ApplicationSubTypeDTO();
    healthInsurance.setParentType(insurance);
    healthInsurance.setApplicationSubTypeTitle("Health Insurance");
    healthInsurance.setApplicationSubTypeCode("HEALTH");
    applicationSubTypeRepository.save(healthInsurance);

    ApplicationSubTypeDTO vehicleInsurance = new ApplicationSubTypeDTO();
    vehicleInsurance.setParentType(insurance);
    vehicleInsurance.setApplicationSubTypeTitle("Vehicle Insurance");
    vehicleInsurance.setApplicationSubTypeCode("VEHICLE");
    applicationSubTypeRepository.save(vehicleInsurance);

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

    List<ApplicationTypeDTO> parentTypes = new ArrayList<>();
    parentTypes.add(mortgage);
    parentTypes.add(insurance);

    List<ApplicationSubTypeDTO> mortgages = new ArrayList<>();
    mortgages.add(residentialLoan);
    mortgages.add(commercialLoan);
    mortgages.add(constructionLoan);
    mortgages.add(businessLoan);

    List<ApplicationSubTypeDTO> insurances = new ArrayList<>();
    insurances.add(healthInsurance);
    insurances.add(vehicleInsurance);

    //generate applications
    for (int i = 0; i < 20; i++) {
      ApplicationDTO application = new ApplicationDTO();
      application.setOwner(user);
      application.setTitle(String.format("%d Application", i));
      application.setDescription(String.format("%d Application Description", i));
      long minDay = LocalDate.of(2019, 1, 1).toEpochDay();
      long randomDay = ThreadLocalRandom.current().nextLong(minDay, LocalDate.now().toEpochDay());
      application.setDateCreated(ofEpochDay(randomDay));

      Random rand = new Random();
      //application.setType(mortgage.getApplicationTypeCode());
      //application.setSubType(residentialLoan.getApplicationSubTypeCode());
      ApplicationTypeDTO currentParentType = parentTypes.get(rand.nextInt(parentTypes.size()));
      application.setType(currentParentType.getApplicationTypeCode());

      if (currentParentType == mortgage) {
        application.setSubType(mortgages.get(rand.nextInt(mortgages.size())).getApplicationSubTypeCode());
        application.setStatus(status1.getApplicationStatusCode());

      }

      if (currentParentType == insurance) {
        application.setSubType(insurances.get(rand.nextInt(insurances.size())).getApplicationSubTypeCode());
        application.setStatus(status4.getApplicationStatusCode());

      }

      if (i % 2 == 0) {
        application.setAgent(agent);
      }
      applicationRepository.save(application);
    }

    // create agents
    List<String> firstNames = Arrays.asList("Kobe", "Lebron", "Michael", "Kyrie", "Ben", "Kawhi", "John");
    List<String> lastNames = Arrays.asList("Bryant", "James", "Jordan", "Irving", "Simmons", "Leonard", "Wall");

    for (int i = 0; i < 20; i++) {
      Random rand = new Random();
      agent = new AccountDTO();
      agent.setUsername(String.format("agent%d", i));
      agent.setFirstName(firstNames.get(rand.nextInt(firstNames.size())));
      agent.setLastName(lastNames.get(rand.nextInt(lastNames.size())));
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
