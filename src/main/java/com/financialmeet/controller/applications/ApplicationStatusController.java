package com.financialmeet.controller.applications;

import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.service.impl.ApplicationStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class ApplicationStatusController {

  @Autowired
  private ApplicationStatusServiceImpl statusServiceImpl;

  @GetMapping
  private ResponseEntity getAllStatuses() {
    return ok(statusServiceImpl.getAllApplicationStatuses());
  }

  @GetMapping("/{id}")
  private ResponseEntity getAllStatuses(@PathVariable Long statusId) {
    return ok(statusServiceImpl.getApplicationStatusById(statusId));
  }

}
