package com.financialmeet.repository;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Page<ApplicationDTO> findAllByTitleContaining(String applicationTitle, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTitleContaining(AccountDTO accountDTO, String applicationTitle, Pageable pageable);

  Page<ApplicationDTO> findByAgentAndTitleContaining(AccountDTO accountDTO, String applicationTitle, Pageable pageable);


}
