package com.financialmeet.repository;

import com.financialmeet.dto.AccountDTO;
import com.financialmeet.dto.ApplicationDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationDTO, Long> {

  Optional<ApplicationDTO> findByOwner(AccountDTO accountDTO);
}
