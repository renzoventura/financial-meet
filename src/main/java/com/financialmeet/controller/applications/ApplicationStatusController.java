package com.financialmeet.controller.applications;

    import static org.springframework.http.ResponseEntity.ok;

    import com.financialmeet.service.impl.ApplicationStatusServiceImpl;
    import com.financialmeet.service.impl.ApplicationTypeServiceImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class ApplicationStatusController {

  @Autowired private ApplicationStatusServiceImpl applicationStatusService;

  @Autowired private ApplicationTypeServiceImpl applicationTypeService;

  @GetMapping
  private ResponseEntity getAllStatuses() {
    return ok(applicationStatusService.getAllApplicationStatuses());
  }

  @GetMapping("/{id}")
  private ResponseEntity getAllStatuses(@PathVariable Long statusId) {
    return ok(applicationStatusService.getApplicationStatusById(statusId));
  }

  @GetMapping("/application-type")
  private ResponseEntity getApplicationStatusesByType(@RequestParam(name = "type") String type) {
    return ok(applicationTypeService.getApplicationStatusesByType(type));
  }
}
