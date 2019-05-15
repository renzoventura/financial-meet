package com.financialmeet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ApplicationTypeDTO {

  public static final String APP_TYPE_MORTGAGE = "MORTGAGE";
  public static final String APP_TYPE_INSURANCE = "INSURANCE";

  @Id
  @Column(name = "app_type_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotNull
  private long id;

  @NotEmpty
  @Column(name = "app_type_code")
  private String applicationTypeCode;

  @NotEmpty
  @Column(name = "app_type_title")
  private String applicationTypeTitle;

  @OneToMany(fetch = FetchType.LAZY)
  @NotNull
  @Column(name = "app_type_statuses")
  private List<StatusDTO> statuses = new ArrayList<>();

  public String getApplicationTypeCode() {
    return applicationTypeCode;
  }

  public void setApplicationTypeCode(String applicationTypeCode) {
    this.applicationTypeCode = applicationTypeCode;
  }

  public List<StatusDTO> getStatuses() {
    return statuses;
  }

  public void setStatuses(List<StatusDTO> statuses) {
    this.statuses = statuses;
  }

  public String getApplicationTypeTitle() {
    return applicationTypeTitle;
  }

  public void setApplicationTypeTitle(String applicationTypeTitle) {
    this.applicationTypeTitle = applicationTypeTitle;
  }

  public long getId() {
    return id;
  }

}
