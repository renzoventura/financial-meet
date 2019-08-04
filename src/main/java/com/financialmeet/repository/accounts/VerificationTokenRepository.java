package com.financialmeet.repository.accounts;

import com.financialmeet.dto.accounts.VerificationTokenDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenDTO, Long> {
  Optional<VerificationTokenDTO> findByVerificationToken(String verificationToken);

}
