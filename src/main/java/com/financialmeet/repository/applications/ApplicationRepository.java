package com.financialmeet.repository.applications;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Page<ApplicationDTO> findAllByTitleContainingAndTypeContainingAndSubTypeContainingOrderByDateCreatedAsc(String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findAllByTitleContainingAndTypeContainingAndSubTypeContainingOrderByDateCreatedDesc(String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTitleContainingAndTypeContainingAndSubTypeContainingOrderByDateCreatedAsc(AccountDTO accountDTO, String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTitleContainingAndTypeContainingAndSubTypeContainingOrderByDateCreatedDesc(AccountDTO accountDTO, String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);


  Page<ApplicationDTO> findByAgentAndTitleContainingAndTypeContainingAndSubTypeContainingOrderByDateCreatedAsc(AccountDTO accountDTO, String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

  Page<ApplicationDTO> findByAgentAndTitleContainingAndTypeContainingAndSubTypeContainingOrderByDateCreatedDesc(AccountDTO accountDTO, String applicationTitle, String applicationType, String applicationSubType, Pageable pageable);

}
