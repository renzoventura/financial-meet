package com.financialmeet.dto.applications;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "application_status")
public class ApplicationStatusDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private long Id;

  @NotEmpty
  private String applicationStatusCode;

  @NotEmpty
  private String applicationStatusTitle;

  public String getApplicationStatusTitle() {
    return applicationStatusTitle;
  }

  public void setApplicationStatusTitle(String applicationStatusTitle) {
    this.applicationStatusTitle = applicationStatusTitle;
  }

  public long getId() {
    return Id;
  }

  public String getApplicationStatusCode() {
    return applicationStatusCode;
  }

  public void setApplicationStatusCode(String applicationStatusCode) {
    this.applicationStatusCode = applicationStatusCode;
  }

}
