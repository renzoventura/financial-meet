package com.financialmeet.service;

import com.financialmeet.dto.ApplicationDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationService {

  Iterable<ApplicationDTO> getAllApplications(String applicationTitle, Integer page, Integer size);

  ApplicationDTO getApplicationById(Long applicationId);

  Iterable<ApplicationDTO> getApplicationsByOwner(
      UserDetails userDetails, String applicationTitle, Integer page, Integer size);

  ApplicationDTO createApplication(ApplicationDTO applicationDTO, UserDetails userDetails);

  ApplicationDTO assignAgentToApplication(Long applicationId, Long agentId);

  ApplicationDTO assignInternalToApplication(Long applicationId, Long agentId);

  ApplicationDTO removeAgentFromApplication(Long applicationId);

  Iterable<ApplicationDTO> getApplicationsByAgent(
      UserDetails userDetails, String applicationTitle, Integer page, Integer size);

  ApplicationDTO createApplicationWithType(
      String applicationType, ApplicationDTO applicationDTO, UserDetails userDetails);

  ApplicationDTO progressApplicationStatus(Long applicationId);
}
