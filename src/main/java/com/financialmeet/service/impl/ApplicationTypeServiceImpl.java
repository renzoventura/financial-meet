package com.financialmeet.service.impl;

import com.financialmeet.dto.ApplicationTypeDTO;
import com.financialmeet.repository.ApplicationTypeRepository;
import com.financialmeet.service.ApplicationTypeService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationTypeServiceImpl implements ApplicationTypeService {

  @Autowired private ApplicationTypeRepository applicationTypeRepository;

  @Override
  public Iterable<ApplicationTypeDTO> getAllApplicationTypes() {
    return applicationTypeRepository.findAll();
  }

  @Override
  public ApplicationTypeDTO getApplicationTypeById(Long applicationTypeId) {
    Optional<ApplicationTypeDTO> applicationTypeDTO =
        applicationTypeRepository.findById(applicationTypeId);
    if (applicationTypeDTO.isPresent()) {
      return applicationTypeDTO.get();
    } else {
      return null;
    }
  }
}
