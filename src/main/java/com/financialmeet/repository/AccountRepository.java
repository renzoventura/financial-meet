package com.financialmeet.repository;

import com.financialmeet.dto.AccountDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountDTO, Long> {
  Optional<AccountDTO> findByUsername(String username);

  Iterable<AccountDTO> findByRolesIn (String role);
}
