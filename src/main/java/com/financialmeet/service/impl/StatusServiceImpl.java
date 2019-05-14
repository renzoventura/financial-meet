package com.financialmeet.service.impl;

import com.financialmeet.dto.StatusDTO;
import com.financialmeet.repository.StatusRepository;
import com.financialmeet.service.StatusService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {

  @Autowired
  private StatusRepository statusRepository;

  @Override
  public Iterable<StatusDTO> getAllStatuses() {
    return statusRepository.findAll();
  }

  @Override
  public StatusDTO getStatusById(Long statusId) {
    Optional<StatusDTO> statusDTO = statusRepository.findById(statusId);
    if (statusDTO.isPresent()) {
      return statusDTO.get();
    } else {
      return null;
    }
  }

}
