package com.financialmeet.repository;

import com.financialmeet.dto.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusDTO, Long> {

}
