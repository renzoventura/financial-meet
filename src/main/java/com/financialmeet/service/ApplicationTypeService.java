package com.financialmeet.service;

import com.financialmeet.dto.applications.ApplicationStatusDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;

public interface ApplicationTypeService {

  Iterable<ApplicationTypeDTO> getAllApplicationTypes();

  ApplicationTypeDTO getApplicationTypeById(Long applicationTypeId);

  Iterable<String> getAllApplicationTypeTitles();

  Iterable<ApplicationStatusDTO> getApplicationStatusesByType(String type);

}
