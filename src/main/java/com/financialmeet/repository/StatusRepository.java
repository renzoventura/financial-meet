package com.financialmeet.repository;

import com.financialmeet.dto.StatusDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusDTO, Long> {

  Optional<StatusDTO> findByStatusCode(String statusCode);

  Optional<StatusDTO> findByStatusTitle(String statusTitle);

}
