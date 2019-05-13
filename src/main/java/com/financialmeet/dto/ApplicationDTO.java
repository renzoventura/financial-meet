package com.financialmeet.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "APPLICATIONS")
public class ApplicationDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @NotNull
  private long id;

  @NotEmpty
  private String title;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "OWNER", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AccountDTO owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AGENT")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AccountDTO agent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "INTERNAL")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AccountDTO internal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "APPLICATION_TYPE")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ApplicationTypeDTO applicationTypeDTO;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "APPLICATION_STATUS")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private StatusDTO statusDTO;

  public StatusDTO getStatusDTO() {
    return statusDTO;
  }

  public void setStatusDTO(StatusDTO statusDTO) {
    this.statusDTO = statusDTO;
  }

  public long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public AccountDTO getAgent() {
    return agent;
  }

  public void setAgent(AccountDTO agent) {
    this.agent = agent;
  }

  public AccountDTO getInternal() {
    return internal;
  }

  public void setInternal(AccountDTO internal) {
    this.internal = internal;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public AccountDTO getOwner() {
    return owner;
  }

  public void setOwner(AccountDTO owner) {
    this.owner = owner;
  }

  public ApplicationTypeDTO getApplicationTypeDTO() {
    return applicationTypeDTO;
  }

  public void setApplicationTypeDTO(ApplicationTypeDTO applicationTypeDTO) {
    this.applicationTypeDTO = applicationTypeDTO;
  }
}
