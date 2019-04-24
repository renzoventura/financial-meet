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

  @GetMapping("/current")
  private ResponseEntity getApplicationByOwner(@AuthenticationPrincipal UserDetails userDetails) {
    return ok(applicationServiceImpl.getApplicationsByOwner(userDetails));
  }

  @GetMapping("/current/agent")
  private ResponseEntity getApplicationByAgent(@AuthenticationPrincipal UserDetails userDetails) {
    return ok(applicationServiceImpl.getApplicationsByAgent(userDetails));
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

  @PutMapping("/{applicationId}/assign/agent/{agentId}")
  private ResponseEntity assignAgentToApplication(
      @PathVariable("applicationId") Long applicationId, @PathVariable Long agentId) {
    try {
      ApplicationDTO currentApplication = applicationServiceImpl.assignAgentToApplication(applicationId, agentId);
      if (currentApplication != null) {
        return ok(currentApplication);
      } else {
        return badRequest().body("Unable to assign agent to application.");
      }
    } catch (Exception e) {
      return badRequest().body("Something went wrong!");
    }
  }

  @PutMapping("/{applicationId}/assign/internal/{internalId}")
  private ResponseEntity assignInternalToApplication(
      @PathVariable("applicationId") Long applicationId, @PathVariable Long internalId) {
    try {
      ApplicationDTO currentApplication = applicationServiceImpl.assignInternalToApplication(applicationId, internalId);
      if (currentApplication != null) {
        return ok(currentApplication);
      } else {
        return badRequest().body("Unable to assign agent to application.");
      }
    } catch (Exception e) {
      return badRequest().body("Something went wrong!");
    }
  }

  @GetMapping("/{applicationId}/remove/agent")
  private ResponseEntity removeAgentFromApplication(@PathVariable("applicationId") Long applicationId) {
    return ok(applicationServiceImpl.removeAgentFromApplication(applicationId));
  }
}
