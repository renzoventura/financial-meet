package com.financialmeet.repository.accounts;

import com.financialmeet.dto.accounts.AccountDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountDTO, Long> {
  Optional<AccountDTO> findByUsername(String username);

  Iterable<AccountDTO> findByRolesIn (String role);

  Page<AccountDTO> findByFirstNameContainingAndLastNameContainingAndSuburbContainingAndRolesIn(String firstName, String lastName, String Suburb, String role, Pageable pageable);

  Page<AccountDTO> findByFirstNameContainingAndLastNameContainingAndSuburbContainingAndSpecializationsInAndRolesIn(String firstName, String lastName, String Suburb, String specialization, String role, Pageable pageable);
}
