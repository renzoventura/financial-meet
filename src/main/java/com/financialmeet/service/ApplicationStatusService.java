package com.financialmeet.service;

import com.financialmeet.dto.applications.ApplicationStatusDTO;

public interface ApplicationStatusService {

  Iterable<ApplicationStatusDTO> getAllApplicationStatuses();

  ApplicationStatusDTO getApplicationStatusById(Long statusId);

}
