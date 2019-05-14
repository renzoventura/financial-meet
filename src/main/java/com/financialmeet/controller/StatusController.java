package com.financialmeet.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.service.impl.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

  @Autowired
  private StatusServiceImpl statusServiceImpl;

  @GetMapping
  private ResponseEntity getAllStatuses() {
    return ok(statusServiceImpl.getAllStatuses());
  }

  @GetMapping("/{id}")
  private ResponseEntity getAllStatuses(@PathVariable Long statusId) {
    return ok(statusServiceImpl.getStatusById(statusId));
  }

}
