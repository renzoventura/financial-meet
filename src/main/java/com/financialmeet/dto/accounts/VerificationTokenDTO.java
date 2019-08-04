package com.financialmeet.dto.accounts;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VERIFICATION_TOKENS")
public class VerificationTokenDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="TOKEN_ID")
  private long tokenId;

  @Column(name="VERIFICATION_TOKEN")
  private String verificationToken;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @OneToOne(targetEntity = AccountDTO.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "ACCOUNT_ID")
  private AccountDTO user;

  public VerificationTokenDTO(AccountDTO accountDTO) {
    this.user = accountDTO;
    createdDate = new Date();
    verificationToken = UUID.randomUUID().toString();
  }
  public VerificationTokenDTO(){

  }

  public long getTokenId() {
    return tokenId;
  }

  public String getVerificationToken() {
    return verificationToken;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public AccountDTO getUser() {
    return user;
  }

  public void setUser(AccountDTO user) {
    this.user = user;
  }
}
