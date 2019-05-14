package com.financialmeet.service;

import com.financialmeet.dto.StatusDTO;

public interface StatusService {

  Iterable<StatusDTO> getAllStatuses();

  StatusDTO getStatusById(Long statusId);

}
