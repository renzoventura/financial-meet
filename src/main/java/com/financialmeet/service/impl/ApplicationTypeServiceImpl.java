package com.financialmeet.service.impl;

import com.financialmeet.dto.applications.ApplicationStatusDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;
import com.financialmeet.repository.applications.ApplicationTypeRepository;
import com.financialmeet.service.ApplicationTypeService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

  @Override
  public Iterable<String> getAllApplicationTypeTitles() {
    return applicationTypeRepository.findAll().stream().map(ApplicationTypeDTO::getApplicationTypeTitle).collect(
        Collectors.toList());
  }

  @Override
  public List<ApplicationStatusDTO> getApplicationStatusesByType(String type) {
    Optional<ApplicationTypeDTO> applicationTypeDTO =
        applicationTypeRepository.findByApplicationTypeCode(type);

    if (applicationTypeDTO.isPresent()) {
      return applicationTypeDTO.get().getStatuses();
    } else {
      return null;
    }
  }
}
