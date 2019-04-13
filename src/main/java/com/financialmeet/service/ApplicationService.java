package com.financialmeet.service;


import com.financialmeet.dto.ApplicationDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationService {

  Iterable<ApplicationDTO> getAllApplications();

  ApplicationDTO getApplicationById(Long applicationId);

  Iterable<ApplicationDTO> getApplicationsByOwner(UserDetails userDetails);

  ApplicationDTO createApplication(ApplicationDTO applicationDTO, UserDetails userDetails);

  ApplicationDTO assignAgentToApplication (Long applicationId, Long agentId);

  ApplicationDTO assignInternalToApplication(Long applicationId, Long agentId);

}
