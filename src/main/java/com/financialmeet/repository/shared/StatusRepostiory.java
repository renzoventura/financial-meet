package com.financialmeet.repository.shared;

import com.financialmeet.dto.shared.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepostiory extends JpaRepository<Status, Long> {

  Optional<Status> findByStatusCode(String statusCode);
  Optional<Status> findByStatusDescription(String statusDescription);

}
