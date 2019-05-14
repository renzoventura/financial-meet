package com.financialmeet.controller;


import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.service.impl.ApplicationTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application-types")
public class ApplicationTypeController {

  @Autowired
  private ApplicationTypeServiceImpl applicationTypeServiceImpl;

  @GetMapping
  private ResponseEntity getAllApplicationTypes() {
    return ok(applicationTypeServiceImpl.getAllApplicationTypes());
  }

  @GetMapping("/{id}")
  private ResponseEntity getApplicationTypeById(@PathVariable Long applicationTypeId) {
    return ok(applicationTypeServiceImpl.getApplicationTypeById(applicationTypeId));
  }

}
