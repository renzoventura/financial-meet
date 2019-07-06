package com.financialmeet.service.impl;

import com.financialmeet.dto.applications.ApplicationStatusDTO;
import com.financialmeet.repository.applications.ApplicationStatusRepository;
import com.financialmeet.service.ApplicationStatusService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

  @Autowired
  private ApplicationStatusRepository applicationStatusRepository;

  @Override
  public Iterable<ApplicationStatusDTO> getAllApplicationStatuses() {
    return applicationStatusRepository.findAll();
  }

  @Override
  public ApplicationStatusDTO getApplicationStatusById(Long statusId) {
    Optional<ApplicationStatusDTO> applicationStatusDTO = applicationStatusRepository.findById(statusId);
    if (applicationStatusDTO.isPresent()) {
      return applicationStatusDTO.get();
    } else {
      return null;
    }
  }

}
