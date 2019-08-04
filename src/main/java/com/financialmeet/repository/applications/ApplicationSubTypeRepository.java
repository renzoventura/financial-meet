package com.financialmeet.repository.applications;

import com.financialmeet.dto.applications.ApplicationSubTypeDTO;
import com.financialmeet.dto.applications.ApplicationTypeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationSubTypeRepository extends JpaRepository<ApplicationSubTypeDTO, Long> {

  Optional<ApplicationSubTypeDTO> findByApplicationSubTypeTitle(String applicationSubTypeTitle);

  Optional<ApplicationSubTypeDTO> findByApplicationSubTypeCode(String applicationSubTypeCode);

  List<ApplicationSubTypeDTO> findAllByParentType(ApplicationTypeDTO applicationTypeDTO);

}
