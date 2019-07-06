package com.financialmeet.repository.applications;

import com.financialmeet.dto.applications.ApplicationStatusDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatusDTO, Long> {

  Optional<ApplicationStatusDTO> findByApplicationStatusCode(String applicationStatusCode);

  Optional<ApplicationStatusDTO> findByApplicationStatusTitle(String applicationStatusTitle);

}
