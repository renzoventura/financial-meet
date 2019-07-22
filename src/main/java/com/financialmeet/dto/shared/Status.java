package com.financialmeet.dto.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "STATUS")
public class Status {

  public static final String ACTIVE_DESC = "Currently active account.";
  public static final String UNVERIFIED_DESC = "Account has yet to verify email.";
  public static final String ARCHIVED_DESC = "Archived.";
  public static final String ACTIVE_CODE = "ACTIVE";
  public static final String UNVERIFIED_CODE = "UNVERIFIED";
  public static final String ARCHIVED_CODE = "ARCHIVED";

  @Id
  @Column(name = "STATID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private long id;

  @NotEmpty
  @Column(name = "STATCODE")
  private String statusCode;

  @NotEmpty
  @Column(name = "STATTITLE")
  private String statusDescription;

  public long getId() {
    return id;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }
}
