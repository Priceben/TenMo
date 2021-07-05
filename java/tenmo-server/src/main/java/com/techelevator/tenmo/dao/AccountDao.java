package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;

public interface AccountDao {

  public BigDecimal getBalance(Long userId);
  public BigDecimal addToBalance(BigDecimal amount, Long userId);
  public BigDecimal subtractFromBalance(BigDecimal amount, Long userId);
  public Long getAccountIdByUser(Long userId);


}
