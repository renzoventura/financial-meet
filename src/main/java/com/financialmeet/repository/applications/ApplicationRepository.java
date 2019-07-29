package com.financialmeet.repository.applications;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Page<ApplicationDTO> findAllByTypeContainingAndSubTypeContaining(String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findAllByIdIn(Long id, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTypeContainingAndSubTypeContaining(AccountDTO accountDTO, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findByAgentAndTypeContainingAndSubTypeContaining(AccountDTO accountDTO, String applicationType, String applicationSubType, Pageable pageable);

}
