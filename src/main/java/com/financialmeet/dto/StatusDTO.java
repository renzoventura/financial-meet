package com.financialmeet.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "STATUS")
public class StatusDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotNull
  private long Id;

  @NotEmpty
  private String statusCode;

  public long getId() {
    return Id;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

}
