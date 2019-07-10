package com.financialmeet.controller.applications;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.dto.applications.ApplicationDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {

  @Autowired private ApplicationServiceImpl applicationServiceImpl;

  @GetMapping("/i/all")
  private ResponseEntity getAllApplications(
      @RequestParam(value = "title", required = false) String applicationTitle,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "subType", required = false) String subType,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "order", required = false) String order) {
    return ok(applicationServiceImpl.getAllApplications(applicationTitle, type,
        subType, page, size, order));
  }

  @GetMapping("/{id}")
  private ResponseEntity getApplicationById(@PathVariable Long applicationId) {
    return ok(applicationServiceImpl.getApplicationById(applicationId));
  }

  @GetMapping("/u/current")
  private ResponseEntity getApplicationsByOwner(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(value = "title", required = false) String applicationTitle,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "subType", required = false) String subType,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "order", required = false) String order) {
    return ok(
        applicationServiceImpl.getApplicationsByOwner(userDetails, applicationTitle, type,
            subType, page, size, order));
  }

  @GetMapping("/a/current")
  private ResponseEntity getApplicationByAgent(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(value = "title", required = false) String applicationTitle,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "subType", required = false) String subType,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size,
      @RequestParam(value = "order", required = false) String order) {
    return ok(
        applicationServiceImpl.getApplicationsByAgent(userDetails, applicationTitle, type,
            subType, page, size, order));
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
      ApplicationDTO currentApplication =
          applicationServiceImpl.assignAgentToApplication(applicationId, agentId);
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
      ApplicationDTO currentApplication =
          applicationServiceImpl.assignInternalToApplication(applicationId, internalId);
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
  private ResponseEntity removeAgentFromApplication(
      @PathVariable("applicationId") Long applicationId) {
    return ok(applicationServiceImpl.removeAgentFromApplication(applicationId));
  }

  @PostMapping("/create/{applicationType}/{applicationSubType}")
  private ResponseEntity createApplicationWithType(
      @PathVariable("applicationType")  String applicationType,
      @PathVariable("applicationSubType")  String applicationSubType,
      @RequestBody ApplicationDTO applicationDTO,
      @AuthenticationPrincipal UserDetails userDetails) {
    try {
      return ok(
          applicationServiceImpl.createApplicationWithType(
              applicationType, applicationSubType, applicationDTO, userDetails));
    } catch (Exception e) {
      return badRequest().body("application creation failed");
    }
  }

  @PostMapping("/progress/{applicationId}")
  private ResponseEntity progressApplicationStatus(
      @PathVariable("applicationId") Long applicationId) {
    try {
      return ok(applicationServiceImpl.progressApplicationStatus(applicationId));
    } catch (Exception e) {
      return badRequest().body("This application cannot progress any further");
    }
  }
}
