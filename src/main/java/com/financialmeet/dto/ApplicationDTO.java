package com.financialmeet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

  private BigDecimal annualIncome;

  private BigDecimal rates;

  private boolean existingMortgage;

  private boolean rent;

  private Integer numberOfChildren;

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
  @JoinColumn(name = "APPLICATIONTYPE")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ApplicationTypeDTO applicationTypeDTO;

  public long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAnnualIncome() {
    return annualIncome;
  }

  public void setAnnualIncome(BigDecimal annualIncome) {
    this.annualIncome = annualIncome;
  }

  public BigDecimal getRates() {
    return rates;
  }

  public void setRates(BigDecimal rates) {
    this.rates = rates;
  }

  public boolean hasExistingMortgage() {
    return existingMortgage;
  }

  public void setExistingMortgage(boolean existingMortgage) {
    this.existingMortgage = existingMortgage;
  }

  public boolean isRent() {
    return rent;
  }

  public void setRent(boolean rent) {
    this.rent = rent;
  }

  public Integer getNumberOfChildren() {
    return numberOfChildren;
  }

  public void setNumberOfChildren(Integer numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
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
