package com.financialmeet.repository;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Iterable<ApplicationDTO> findByOwner(AccountDTO accountDTO);

  Iterable<ApplicationDTO> findByAgent(AccountDTO accountDTO);


}
