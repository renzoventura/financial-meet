package com.financialmeet.service.impl;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import com.financialmeet.repository.AccountRepository;
import com.financialmeet.repository.ApplicationRepository;
import com.financialmeet.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
  public ApplicationDTO getApplicationByOwnerId(Long ownerId) {
    // check if owner exist first
    if (accountRepository.findById(ownerId).isPresent()) {
      // check if an application is owned by an owner
      AccountDTO owner = accountRepository.findById(ownerId).get();
      if (applicationRepository.findByOwner(owner).isPresent()) {
        return applicationRepository.findByOwner(owner).get();
      } else {
        return null;
      }
    }
    return null;
  }

  @Override
  public ResponseEntity createApplication(ApplicationDTO applicationDTO, UserDetails userDetails) {

    if (accountRepository.findByUsername(userDetails.getUsername()).isPresent()) {
      AccountDTO currentAccount = accountRepository.findByUsername(userDetails.getUsername()).get();

      if (!currentAccount.getRoles().contains(AccountDTO.ACCOUNT_ROLE_USER)) {
        return badRequest().body("not correct account type");
      }

      applicationDTO.setOwner(accountRepository.findByUsername(userDetails.getUsername()).get());
      applicationRepository.save(applicationDTO);
      return ok(applicationDTO);
    } else {
      return badRequest().body("Account Not found");
    }
  }

  @Override
  public ResponseEntity assignAgentToApplication(Long applicationId, UserDetails userDetails) {

    if (accountRepository.findByUsername(userDetails.getUsername()).isPresent()
        && applicationRepository.findById(applicationId).isPresent()) {
      AccountDTO currentAccount = accountRepository.findByUsername(userDetails.getUsername()).get();
      ApplicationDTO currentApplication = applicationRepository.findById(applicationId).get();

      if (!currentAccount.getRoles().contains(AccountDTO.ACCOUNT_ROLE_AGENT)) {
        return badRequest().body("not correct account type");
      }
      currentApplication.setAgent(
          accountRepository.findByUsername(userDetails.getUsername()).get());
      applicationRepository.save(currentApplication);
      return ok(currentApplication);
    } else {
      return badRequest().body("Account Not found");
    }
  }
}
