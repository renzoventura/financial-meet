package com.financialmeet.repository;

import com.financialmeet.dto.ApplicationTypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationTypeRepository extends JpaRepository<ApplicationTypeDTO, Long> {

}
