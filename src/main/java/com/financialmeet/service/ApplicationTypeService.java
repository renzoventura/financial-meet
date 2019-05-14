package com.financialmeet.service;

import com.financialmeet.dto.ApplicationTypeDTO;

public interface ApplicationTypeService {

  Iterable<ApplicationTypeDTO> getAllApplicationTypes();

  ApplicationTypeDTO getApplicationTypeById(Long applicationTypeId);

}
