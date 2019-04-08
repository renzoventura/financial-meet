package com.financialmeet;

import com.financialmeet.model.Account;
import com.financialmeet.repository.AccountRepository;
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
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    ArrayList<Account> accounts = new ArrayList<>(2);

    Account user = new Account();
    user.setUsername("user");
    user.setPassword(this.passwordEncoder.encode("password"));
    user.setRoles(Collections.singletonList("ROLE_USER"));

    accountRepository.save(user);

  }

}
