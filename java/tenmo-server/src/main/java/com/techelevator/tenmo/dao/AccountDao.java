package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;

public interface AccountDao {

  public double getBalance(int userId);
  public double addToBalance(double amount, int userId);
  public double subtractFromBalance(double amount, int userId);
  public Accounts findByUserId(int userId);
  public int getAccountIdByUser(int userId);


}
