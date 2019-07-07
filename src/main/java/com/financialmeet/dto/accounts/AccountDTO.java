package com.financialmeet.dto.accounts;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "ACCOUNTS")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AccountDTO implements UserDetails {

  public static final String ACCOUNT_ROLE_USER = "ROLE_USER";
  public static final String ACCOUNT_ROLE_AGENT = "ROLE_AGENT";
  public static final String ACCOUNT_ROLE_INTERNAL = "ROLE_INTERNAL";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private long Id;

  @NotEmpty
  private String username;

  @NotEmpty
  @JsonIgnore
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  @JsonIgnore
  private List<String> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
  }

  @NotEmpty
  private String firstName;

  @NotEmpty
  private String lastName;

  private String phoneNumber;

  private String mobile;

  private String occupation;

  private String suburb;

  private String email;

  private String annualIncome;

  private String currentLoan;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getSuburb() {
    return suburb;
  }

  public void setSuburb(String suburb) {
    this.suburb = suburb;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAnnualIncome() {
    return annualIncome;
  }

  public void setAnnualIncome(String annualIncome) {
    this.annualIncome = annualIncome;
  }

  public String getCurrentLoan() {
    return currentLoan;
  }

  public void setCurrentLoan(String currentLoan) {
    this.currentLoan = currentLoan;
  }

  @JsonIgnore
  public List<String> getRoles() {
    return roles;
  }

  @Override
  @JsonIgnore
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public long getId() {
    return Id;
  }

}
