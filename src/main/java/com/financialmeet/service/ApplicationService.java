package com.financialmeet.service;


import com.financialmeet.dto.ApplicationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationService {

  Iterable<ApplicationDTO> getAllApplications();

  ApplicationDTO getApplicationById(Long applicationId);

  ApplicationDTO getApplicationByOwnerId(Long applicationId);

  ApplicationDTO createApplication(ApplicationDTO applicationDTO, UserDetails userDetails);

  ApplicationDTO assignAgentToApplication (Long applicationId, UserDetails userDetails);

}
