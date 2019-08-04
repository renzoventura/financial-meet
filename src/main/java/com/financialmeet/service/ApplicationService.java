package com.financialmeet.service;

import com.financialmeet.dto.applications.ApplicationDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationService {

  Iterable<ApplicationDTO> getAllApplications(Long id, String type, String subType, Integer page, Integer size, String order);

  ApplicationDTO getApplicationById(Long applicationId);

  Iterable<ApplicationDTO> getApplicationsByOwner(
      UserDetails userDetails, Long id, String type, String subType, Integer page, Integer size, String order);

  ApplicationDTO createApplication(ApplicationDTO applicationDTO, UserDetails userDetails);

  ApplicationDTO assignAgentToApplication(Long applicationId, Long agentId);

  ApplicationDTO assignInternalToApplication(Long applicationId, Long agentId);

  ApplicationDTO removeAgentFromApplication(Long applicationId);

  Iterable<ApplicationDTO> getApplicationsByAgent(
      UserDetails userDetails, Long id, String type, String subType, Integer page, Integer size, String order);

  ApplicationDTO createApplicationWithType(
      String applicationType, String applicationSubType, ApplicationDTO applicationDTO, UserDetails userDetails);

  ApplicationDTO progressApplicationStatus(Long applicationId);
}
