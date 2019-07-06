package com.financialmeet.repository.applications;

import com.financialmeet.dto.applications.ApplicationTypeDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationTypeRepository extends JpaRepository<ApplicationTypeDTO, Long> {

  Optional<ApplicationTypeDTO> findByApplicationTypeTitle(String applicationTypeTitle);

  Optional<ApplicationTypeDTO> findByApplicationTypeCode(String applicationTypeCode);

}
