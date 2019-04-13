package com.financialmeet.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.ApplicationDTO;
import com.financialmeet.service.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {

  @Autowired private ApplicationServiceImpl applicationServiceImpl;

  @GetMapping
  private ResponseEntity getAllApplications() {
    return ok(applicationServiceImpl.getAllApplications());
  }

  @GetMapping("/{id}")
  private ResponseEntity getApplicationById(@PathVariable Long applicationId) {
    return ok(applicationServiceImpl.getApplicationById(applicationId));
  }

  @GetMapping("/owner/{ownerId}")
  private ResponseEntity getApplicationByOwnerId(@PathVariable("ownerId") Long ownerId) {
    return ok(applicationServiceImpl.getApplicationByOwnerId(ownerId));
  }

  @PostMapping("/create")
  private ResponseEntity createApplication(
      @RequestBody ApplicationDTO applicationDTO,
      @AuthenticationPrincipal UserDetails userDetails) {
    try {
      return ok(applicationServiceImpl.createApplication(applicationDTO, userDetails));
    } catch (Exception e) {
      return badRequest().body("application creation failed");
    }
  }

  @PutMapping("/{applicationId}/assign/agent")
  private ResponseEntity assignAgentToApplication(
      @PathVariable("applicationId") Long applicationId,
      @AuthenticationPrincipal UserDetails userDetails) {
    try {
      return ok(applicationServiceImpl.assignAgentToApplication(applicationId, userDetails));
    } catch (Exception e) {
      return badRequest().body("application creation failed");
    }
  }
}
