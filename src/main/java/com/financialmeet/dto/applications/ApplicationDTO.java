package com.financialmeet.dto.applications;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.financialmeet.dto.accounts.AccountDTO;
import java.time.LocalDate;
import javax.persistence.Column;
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
@Table(name = "applications")
public class ApplicationDTO {

  @Id
  @Column(name = "application_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private long id;

  @Column(name = "application_title")
  private String title;

  @Column(name = "application_description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "application_owner", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AccountDTO owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_agent")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AccountDTO agent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_internal")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AccountDTO internal;

  private String type;

  private String subType;

  private String status;

  @JsonFormat(pattern="dd-MM-yyyy")
  private LocalDate dateCreated;

  //remove later for future information
  private String annualIncome;

  private String overtimeBonus;

  private String rentalIncome;

  private String businessIncome;

  private String otherIncome;

  private String expectedLoan;

  private String loan;

  private String otherInformation;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(LocalDate dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getSubType() {
    return subType;
  }

  public void setSubType(String subType) {
    this.subType = subType;
  }

  public String getAnnualIncome() {
    return annualIncome;
  }

  public void setAnnualIncome(String annualIncome) {
    this.annualIncome = annualIncome;
  }

  public String getOvertimeBonus() {
    return overtimeBonus;
  }

  public void setOvertimeBonus(String overtimeBonus) {
    this.overtimeBonus = overtimeBonus;
  }

  public String getRentalIncome() {
    return rentalIncome;
  }

  public void setRentalIncome(String rentalIncome) {
    this.rentalIncome = rentalIncome;
  }

  public String getBusinessIncome() {
    return businessIncome;
  }

  public void setBusinessIncome(String businessIncome) {
    this.businessIncome = businessIncome;
  }

  public String getOtherIncome() {
    return otherIncome;
  }

  public void setOtherIncome(String otherIncome) {
    this.otherIncome = otherIncome;
  }

  public String getExpectedLoan() {
    return expectedLoan;
  }

  public void setExpectedLoan(String expectedLoan) {
    this.expectedLoan = expectedLoan;
  }

  public String getLoan() {
    return loan;
  }

  public void setLoan(String loan) {
    this.loan = loan;
  }

  public String getOtherInformation() {
    return otherInformation;
  }

  public void setOtherInformation(String otherInformation) {
    this.otherInformation = otherInformation;
  }
}
