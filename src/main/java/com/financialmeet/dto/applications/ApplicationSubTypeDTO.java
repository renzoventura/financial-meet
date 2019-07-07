package com.financialmeet.dto.applications;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ApplicationSubTypeDTO {

  @Id
  @Column(name = "app_subtype_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotNull
  private long id;

  @NotEmpty
  @Column(name = "app_subtype_code")
  private String applicationSubTypeCode;

  @NotEmpty
  @Column(name = "app_subtype_title")
  private String applicationSubTypeTitle;

  @NotNull
  @OneToOne
  private ApplicationTypeDTO parentType;

  public long getId() {
    return id;
  }

  public String getApplicationSubTypeCode() {
    return applicationSubTypeCode;
  }

  public void setApplicationSubTypeCode(String applicationSubTypeCode) {
    this.applicationSubTypeCode = applicationSubTypeCode;
  }

  public String getApplicationSubTypeTitle() {
    return applicationSubTypeTitle;
  }

  public void setApplicationSubTypeTitle(String applicationSubTypeTitle) {
    this.applicationSubTypeTitle = applicationSubTypeTitle;
  }

  public ApplicationTypeDTO getParentType() {
    return parentType;
  }

  public void setParentType(
      ApplicationTypeDTO parentType) {
    this.parentType = parentType;
  }
}
