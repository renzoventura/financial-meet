package com.financialmeet.service.impl;

import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_AGENT;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_INTERNAL;
import static com.financialmeet.dto.accounts.AccountDTO.ACCOUNT_ROLE_USER;
import static com.financialmeet.util.PaginationUtil.createPageRequest;
import static com.financialmeet.util.PaginationUtil.getDirection;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import com.financialmeet.dto.applications.ApplicationSubTypeDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;
import com.financialmeet.dto.applications.ApplicationStatusDTO;
import com.financialmeet.repository.accounts.AccountRepository;
import com.financialmeet.repository.applications.ApplicationRepository;
import com.financialmeet.repository.applications.ApplicationStatusRepository;
import com.financialmeet.repository.applications.ApplicationSubTypeRepository;
import com.financialmeet.repository.applications.ApplicationTypeRepository;
import com.financialmeet.service.ApplicationService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  private final String ASCENDING = "ASC";
  private final String DESCENDING = "DESC";
  private final int LAST_ELEMENT_IN_ARRAY_INDEX = -1;
  private final String APPLICATION_ATTRIBUTE_DATE_CREATED = "dateCreated";

  @Autowired private ApplicationRepository applicationRepository;

  @Autowired private AccountRepository accountRepository;

  @Autowired private ApplicationTypeRepository applicationTypeRepository;

  @Autowired private ApplicationStatusRepository applicationStatusRepository;

  @Autowired private ApplicationSubTypeRepository applicationSubTypeRepository;

  @Autowired
  private ApplicationTypeServiceImpl applicationTypeService;

  @Override
  public Iterable<ApplicationDTO> getAllApplications(
      String applicationTitle, String type, String subType, Integer page, Integer size, String order) {

    //change these to enum waste of read
    Optional<ApplicationTypeDTO> currentApplicationType =
        applicationTypeRepository.findByApplicationTypeTitle(type.toUpperCase());
    Optional<ApplicationSubTypeDTO> currentApplicationSubType =
        applicationSubTypeRepository.findByApplicationSubTypeTitle(
            subType.toUpperCase());

    type = currentApplicationType.isPresent() ? currentApplicationType.get().getApplicationTypeCode() : "";
    subType = currentApplicationSubType.isPresent() ? currentApplicationSubType.get().getApplicationSubTypeCode() : "";

    if (!order.isEmpty()) {
      Pageable pageable = createPageRequest(page, size, getDirection(order), APPLICATION_ATTRIBUTE_DATE_CREATED);
      return applicationRepository.findAllByTitleContainingAndTypeContainingAndSubTypeContaining(
          applicationTitle, type, subType, pageable);
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
    List<ApplicationStatusDTO> statuses = applicationTypeService.getApplicationStatusesByType(newApplication.getType());

    String lastStatusInStatuses = statuses.get(statuses.size()-1).getApplicationStatusCode();

    if (!lastStatusInStatuses.equalsIgnoreCase(newApplication.getStatus())) {
      newApplication.setAgent(null);
      return applicationRepository.save(newApplication);
    }
    return null;
  }

  @Override
  public Iterable<ApplicationDTO> getApplicationsByAgent(
      UserDetails userDetails, String applicationTitle, String type, String subType, Integer page, Integer size, String order) {
    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());

    //change these to enum waste of read
    Optional<ApplicationTypeDTO> currentApplicationType =
        applicationTypeRepository.findByApplicationTypeTitle(type.toUpperCase());
    Optional<ApplicationSubTypeDTO> currentApplicationSubType =
        applicationSubTypeRepository.findByApplicationSubTypeTitle(
            subType.toUpperCase());

    order = !order.isEmpty() ? order : DESCENDING;

    type = currentApplicationType.isPresent() ? currentApplicationType.get().getApplicationTypeCode() : "";
    subType = currentApplicationSubType.isPresent() ? currentApplicationSubType.get().getApplicationSubTypeCode() : "";

    if (currentAccount.isPresent()) {
      if (!order.isEmpty()) {
        Pageable pageable = createPageRequest(page, size, getDirection(order), APPLICATION_ATTRIBUTE_DATE_CREATED);
        return applicationRepository.findByAgentAndTitleContainingAndTypeContainingAndSubTypeContaining(
            currentAccount.get(), applicationTitle, type, subType, pageable);
      }
    }
    return null;
  }

  @Override
  public ApplicationDTO createApplicationWithType(
      String applicationType,
      String applicationSubType,
      ApplicationDTO applicationDTO,
      UserDetails userDetails) {

    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());

    Optional<ApplicationTypeDTO> currentApplicationType =
        applicationTypeRepository.findByApplicationTypeTitle(applicationType.toUpperCase());

    Optional<ApplicationSubTypeDTO> currentApplicationSubType =
        applicationSubTypeRepository.findByApplicationSubTypeTitle(
            applicationSubType.toUpperCase());

    if (currentAccount.isPresent()
        && currentAccount.get().getRoles().contains(ACCOUNT_ROLE_USER)
        && currentApplicationType.isPresent()
        && currentApplicationSubType.isPresent()) {

      applicationDTO.setType(currentApplicationType.get().getApplicationTypeCode());

      // check if sub type and parent type matches
      if (currentApplicationSubType.get().getParentType() == currentApplicationType.get()) {
        applicationDTO.setSubType(
            currentApplicationSubType.get().getApplicationSubTypeCode());
      } else {
        return null;
      }

      applicationDTO.setStatus(
          currentApplicationType
              .get()
              .getStatuses()
              .get(0)
              .getApplicationStatusCode()); // set first
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
      Optional<ApplicationStatusDTO> currentApplicationStatus =
          applicationStatusRepository.findByApplicationStatusCode(
              currentApplication.get().getStatus());
      Optional<ApplicationTypeDTO> currentApplicationType =
          applicationTypeRepository.findByApplicationTypeCode(
              currentApplication.get().getType());

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
                      .getApplicationStatusCode());

          applicationRepository.save(currentApplication.get());
          return currentApplication.get();
        }
      }
    }
    return null;
  }

  @Override
  public Iterable<ApplicationDTO> getApplicationsByOwner(
      UserDetails userDetails, String applicationTitle, String type, String subType, Integer page, Integer size, String order) {
    Optional<AccountDTO> currentAccount =
        accountRepository.findByUsername(userDetails.getUsername());

    //change these to enum waste of read
    Optional<ApplicationTypeDTO> currentApplicationType =
        applicationTypeRepository.findByApplicationTypeTitle(type.toUpperCase());
    Optional<ApplicationSubTypeDTO> currentApplicationSubType =
        applicationSubTypeRepository.findByApplicationSubTypeTitle(
            subType.toUpperCase());

    order = !order.isEmpty() ? order : DESCENDING;

    type = currentApplicationType.isPresent() ? currentApplicationType.get().getApplicationTypeCode() : "";
    subType = currentApplicationSubType.isPresent() ? currentApplicationSubType.get().getApplicationSubTypeCode() : "";

    if (currentAccount.isPresent()) {
      if (!order.isEmpty()) {
        Pageable pageable = createPageRequest(page, size, getDirection(order), APPLICATION_ATTRIBUTE_DATE_CREATED);
        return applicationRepository.findByOwnerAndTitleContainingAndTypeContainingAndSubTypeContaining(
            currentAccount.get(), applicationTitle, type, subType, pageable);
      }
    }
    return null;
  }
}
