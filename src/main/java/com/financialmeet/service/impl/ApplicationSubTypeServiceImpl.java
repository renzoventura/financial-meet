package com.financialmeet.service.impl;

import com.financialmeet.dto.applications.ApplicationSubTypeDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;
import com.financialmeet.repository.applications.ApplicationSubTypeRepository;
import com.financialmeet.repository.applications.ApplicationTypeRepository;
import com.financialmeet.service.ApplicationSubTypeService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationSubTypeServiceImpl implements ApplicationSubTypeService {

  @Autowired
  private ApplicationTypeRepository applicationTypeRepository;

  @Autowired
  private ApplicationSubTypeRepository applicationSubTypeRepository;

  @Override
  public Iterable<String> getAllApplicationSubTypeCode() {
    return applicationSubTypeRepository.findAll().stream().map(ApplicationSubTypeDTO::getApplicationSubTypeCode).collect(
        Collectors.toList());
  }

  @Override
  public List<String> getAllApplicationSubTypeTitleByParent(String parentApplicationTitle) {
    Optional<ApplicationTypeDTO> parentApplication = applicationTypeRepository.findByApplicationTypeTitle(parentApplicationTitle.toUpperCase());
    if (parentApplication.isPresent()) {
      //applicationSubTypeRepository.findAllByParentType(parentApplication.get().getId());
      return applicationSubTypeRepository.findAllByParentType(parentApplication.get()).stream().map(ApplicationSubTypeDTO::getApplicationSubTypeTitle).collect(
          Collectors.toList());
    }
    return null;
  }
}
