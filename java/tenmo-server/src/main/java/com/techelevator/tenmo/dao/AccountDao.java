package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;

public interface AccountDao {

  public BigDecimal getBalance(int userId);
  public BigDecimal addToBalance(BigDecimal amount, int userId);
  public BigDecimal subtractFromBalance(BigDecimal amount, int userId);
  public Accounts findByUserId(int userId);


}
