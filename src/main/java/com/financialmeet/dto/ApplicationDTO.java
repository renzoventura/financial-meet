package com.financialmeet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @NotEmpty private String title;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "OWNER", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private AccountDTO owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AGENT")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private AccountDTO agent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "INTERNAL")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private AccountDTO internal;

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
}
