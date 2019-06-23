package com.financialmeet.service.impl;

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
import com.financialmeet.service.ApplicationService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  private final String ASCENDING = "ASC";
  private final String DESCENDING = "DESC";

  @Autowired private ApplicationRepository applicationRepository;

  @Autowired private AccountRepository accountRepository;

  @Autowired private ApplicationTypeRepository applicationTypeRepository;

  @Autowired private StatusRepository statusRepository;

  @Override
  public Iterable<ApplicationDTO> getAllApplications(
      String applicationTitle, Integer page, Integer size, String order) {

    if (!order.isEmpty()) {
      if (order.equalsIgnoreCase(DESCENDING)) {
        return applicationRepository.findAllByTitleContainingOrderByDateCreatedDesc(
            applicationTitle, PageRequest.of(page, size));
      } else {
        return applicationRepository.findAllByTitleContainingOrderByDateCreatedAsc(
            applicationTitle, PageRequest.of(page, size));
      }
    }
    return null;

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
  public ApplicationDTO createApplication(ApplicationDTO applicationDTO, UserDetails userDetails) {

    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());

    if (currentAccount.isPresent()) {
      if (currentAccount.get().getRoles().contains(ACCOUNT_ROLE_USER)) {
        applicationDTO.setOwner(currentAccount.get());
        applicationRepository.save(applicationDTO);
        return applicationDTO;
      }
    }
    return null;
  }

  @Override
  public ApplicationDTO assignAgentToApplication(Long applicationId, Long agentId) {

    Optional<AccountDTO> currentAccount = accountRepository.findById(agentId);
    Optional<ApplicationDTO> currentApplication = applicationRepository.findById(applicationId);

    if (currentAccount.isPresent() && currentApplication.isPresent()) {
      if (currentAccount.get().getRoles().contains(ACCOUNT_ROLE_AGENT)) {

        currentApplication.get().setAgent(currentAccount.get());
        applicationRepository.save(currentApplication.get());

        return currentApplication.get();
      }
    }
    return null;
  }

  @Override
  public ApplicationDTO assignInternalToApplication(Long applicationId, Long internalId) {
    Optional<AccountDTO> currentAccount = accountRepository.findById(internalId);
    Optional<ApplicationDTO> currentApplication = applicationRepository.findById(applicationId);

    if (currentAccount.isPresent() && currentApplication.isPresent()) {
      if (currentAccount.get().getRoles().contains(ACCOUNT_ROLE_INTERNAL)) {

        currentApplication.get().setInternal(currentAccount.get());
        applicationRepository.save(currentApplication.get());

        return currentApplication.get();
      }
    }
    return null;
  }

  @Override
  public ApplicationDTO removeAgentFromApplication(Long applicationId) {
    ApplicationDTO newApplication = applicationRepository.getOne(applicationId);
    newApplication.setAgent(null);
    return applicationRepository.save(newApplication);
  }

  @Override
  public Iterable<ApplicationDTO> getApplicationsByAgent(
      UserDetails userDetails, String applicationTitle, Integer page, Integer size, String order) {
    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());
    if (currentAccount.isPresent() && !order.isEmpty()) {

      if (order.equalsIgnoreCase(DESCENDING)) {
        return applicationRepository.findByAgentAndTitleContainingOrderByDateCreatedDesc(
            currentAccount.get(), applicationTitle, PageRequest.of(page, size));
      } else {
        return applicationRepository.findByAgentAndTitleContainingOrderByDateCreatedAsc(
            currentAccount.get(), applicationTitle, PageRequest.of(page, size));
      }
    }
    return null;
  }

  @Override
  public ApplicationDTO createApplicationWithType(
      String applicationType, ApplicationDTO applicationDTO, UserDetails userDetails) {

    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());

    Optional<ApplicationTypeDTO> currentApplicationType =
        applicationTypeRepository.findByApplicationTypeTitle(applicationType.toUpperCase());

    if (currentAccount.isPresent() && currentAccount.get().getRoles().contains(ACCOUNT_ROLE_USER)) {
      if (currentApplicationType.isPresent())
        applicationDTO.setApplicationType(currentApplicationType.get().getApplicationTypeCode());
      applicationDTO.setStatus(
          currentApplicationType.get().getStatuses().get(0).getStatusCode()); // set first
      applicationDTO.setOwner(currentAccount.get());
      applicationDTO.setDateCreated(LocalDate.now());
      applicationRepository.save(applicationDTO);
      return applicationDTO;
    }
    return null;
  }

  // lmao this code is so shit please fix it later im so tired
  @Override
  public ApplicationDTO progressApplicationStatus(Long applicationId) {
    Optional<ApplicationDTO> currentApplication = applicationRepository.findById(applicationId);
    if (currentApplication.isPresent()) {
      Optional<StatusDTO> currentApplicationStatus =
          statusRepository.findByStatusCode(currentApplication.get().getStatus());
      Optional<ApplicationTypeDTO> currentApplicationType =
          applicationTypeRepository.findByApplicationTypeCode(
              currentApplication.get().getApplicationType());

      if (currentApplicationStatus.isPresent() && currentApplicationType.isPresent()) {
        int currentProgressIndex =
            currentApplicationType.get().getStatuses().indexOf(currentApplicationStatus.get());

        if (currentApplicationType.get().getStatuses().contains(currentApplicationStatus.get())
            && currentProgressIndex != currentApplicationType.get().getStatuses().size()) {
          currentApplication
              .get()
              .setStatus(
                  currentApplicationType
                      .get()
                      .getStatuses()
                      .get(currentProgressIndex + 1)
                      .getStatusCode());

          applicationRepository.save(currentApplication.get());
          return currentApplication.get();
        }
      }
    }
    return null;
  }

  @Override
  public Iterable<ApplicationDTO> getApplicationsByOwner(
      UserDetails userDetails, String applicationTitle, Integer page, Integer size, String order) {
    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());
    if (currentAccount.isPresent() && !order.isEmpty()) {

      if (order.equalsIgnoreCase(DESCENDING)) {
        return applicationRepository.findByOwnerAndTitleContainingOrderByDateCreatedDesc(
            currentAccount.get(), applicationTitle, PageRequest.of(page, size));
      } else {
        return applicationRepository.findByOwnerAndTitleContainingOrderByDateCreatedAsc(
            currentAccount.get(), applicationTitle, PageRequest.of(page, size));
      }
    }
    return null;
  }
}
