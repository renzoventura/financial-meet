package com.financialmeet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "APP_TYPE")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ApplicationTypeDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotNull
  private long id;

  @NotEmpty
  private String applicationTypeTitle;

  @ManyToMany(fetch = FetchType.LAZY)
  @OrderColumn(name="INDEX")
  @NotNull
  private Set<StatusDTO> statuses = new HashSet<>();

  public Set<StatusDTO> getStatuses() {
    return statuses;
  }

  public void setStatuses(Set<StatusDTO> statuses) {
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
