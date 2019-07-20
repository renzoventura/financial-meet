package com.financialmeet.repository.applications;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Page<ApplicationDTO> findAllByTitleContainingAndTypeContainingAndSubTypeContaining(String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTitleContainingAndTypeContainingAndSubTypeContaining(AccountDTO accountDTO, String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findByAgentAndTitleContainingAndTypeContainingAndSubTypeContaining(AccountDTO accountDTO, String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

}
