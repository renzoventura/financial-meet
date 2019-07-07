package com.financialmeet.controller.applications;

import static org.springframework.http.ResponseEntity.ok;

import com.financialmeet.service.impl.ApplicationSubTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application-sub-types")
public class ApplicationSubTypeController {

  @Autowired
  private ApplicationSubTypeServiceImpl applicationSubTypeServiceImpl;

  @GetMapping("/title")
  private ResponseEntity getAllApplicationSubTypeTitle(
      @RequestParam(value = "parent", required = false) String parentApplicationTitle
  ) {
    return ok(applicationSubTypeServiceImpl.getAllApplicationSubTypeTitleByParent(parentApplicationTitle));
  }


}
