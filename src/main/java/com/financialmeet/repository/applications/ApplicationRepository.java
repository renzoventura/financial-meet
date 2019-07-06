package com.financialmeet.repository.applications;

import com.financialmeet.dto.accounts.AccountDTO;
import com.financialmeet.dto.applications.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Page<ApplicationDTO> findAllByTitleContainingOrderByDateCreatedAsc(String applicationTitle, Pageable pageable);

  Page<ApplicationDTO> findAllByTitleContainingOrderByDateCreatedDesc(String applicationTitle, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTitleContainingOrderByDateCreatedAsc(AccountDTO accountDTO, String applicationTitle, Pageable pageable);

  Page<ApplicationDTO> findByOwnerAndTitleContainingOrderByDateCreatedDesc(AccountDTO accountDTO, String applicationTitle, Pageable pageable);


  Page<ApplicationDTO> findByAgentAndTitleContainingOrderByDateCreatedAsc(AccountDTO accountDTO, String applicationTitle, Pageable pageable);

  Page<ApplicationDTO> findByAgentAndTitleContainingOrderByDateCreatedDesc(AccountDTO accountDTO, String applicationTitle, Pageable pageable);

}
