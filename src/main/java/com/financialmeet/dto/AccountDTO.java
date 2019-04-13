package com.financialmeet.dto;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  @OneToMany(cascade = CascadeType.ALL,
      fetch = FetchType.LAZY, mappedBy = "owner")
  public Set<ApplicationDTO> customerApplications = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL,
      fetch = FetchType.LAZY, mappedBy = "agent")
  public Set<ApplicationDTO> agentAssignedApplications = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL,
      fetch = FetchType.LAZY, mappedBy = "internal")
  public Set<ApplicationDTO> internalAssignedApplications = new HashSet<>();

  public Set<ApplicationDTO> getCustomerApplications() {
    return customerApplications;
  }

  public void setCustomerApplications(
      Set<ApplicationDTO> customerApplications) {
    this.customerApplications = customerApplications;
  }

  public Set<ApplicationDTO> getAgentAssignedApplications() {
    return agentAssignedApplications;
  }

  public void setAgentAssignedApplications(
      Set<ApplicationDTO> agentAssignedApplications) {
    this.agentAssignedApplications = agentAssignedApplications;
  }

  public Set<ApplicationDTO> getInternalAssignedApplications() {
    return internalAssignedApplications;
  }

  public void setInternalAssignedApplications(
      Set<ApplicationDTO> internalAssignedApplications) {
    this.internalAssignedApplications = internalAssignedApplications;
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
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