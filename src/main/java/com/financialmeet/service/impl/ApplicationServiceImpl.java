package com.financialmeet.service.impl;


import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import com.financialmeet.repository.AccountRepository;
import com.financialmeet.repository.ApplicationRepository;
import com.financialmeet.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Autowired private ApplicationRepository applicationRepository;

  @Autowired private AccountRepository accountRepository;

  @Override
  public Iterable<ApplicationDTO> getAllApplications() {
    return applicationRepository.findAll();
  }

  @Override
  public ApplicationDTO getApplicationById(Long applicationId) {
    if (applicationRepository.findById(applicationId).isPresent()) {
      return applicationRepository.findById(applicationId).get();
    } else {
      return null;
    }
  }

  @Override
  public Iterable<ApplicationDTO> getApplicationsByOwner(UserDetails userDetails) {
    Optional<AccountDTO> currentAccount = accountRepository.findByUsername(userDetails.getUsername());

    if (currentAccount.isPresent()) {
      return applicationRepository.findByOwner(currentAccount.get());
    }
    return null;
  }

  @Override
  public ApplicationDTO createApplication(ApplicationDTO applicationDTO, UserDetails userDetails) {

    Optional<AccountDTO> currentAccount = accountRepository.findByUsername(userDetails.getUsername());

    if (currentAccount.isPresent()) {
      if (currentAccount.get().getRoles().contains(AccountDTO.ACCOUNT_ROLE_USER)) {
        applicationDTO.setOwner(currentAccount.get());
        applicationRepository.save(applicationDTO);
        return applicationDTO;
      }
    }
    return null;
  }

  @Override
  public ApplicationDTO assignAgentToApplication(Long applicationId, UserDetails userDetails) {

    Optional<AccountDTO> currentAccount = accountRepository.findByUsername(userDetails.getUsername());
    Optional<ApplicationDTO> currentApplication = applicationRepository.findById(applicationId);

    if (currentAccount.isPresent() && currentApplication.isPresent()) {
      if (currentAccount.get().getRoles().contains(AccountDTO.ACCOUNT_ROLE_AGENT)) {
        currentApplication.get().setAgent(currentAccount.get());
        applicationRepository.save(currentApplication.get());
        return currentApplication.get();
      }
    }
    return null;
  }

}
