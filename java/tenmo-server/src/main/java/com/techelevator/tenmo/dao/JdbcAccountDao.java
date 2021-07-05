package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getBalance(int accountId){
        String sql = "SELECT balance FROM accounts WHERE account_id = ?;";
        double balance = jdbcTemplate.queryForObject(sql, Double.class, accountId);
        return balance;
    }

    @Override
    public double addToBalance(double amount, int accountToId){
          String sql = "UPDATE accounts SET balance = balance + ?  WHERE account_id = ? RETURNING balance;";
          try {
              return jdbcTemplate.queryForObject(sql, Double.class, amount, accountToId);
          } catch(ResourceAccessException re){
              System.out.println("Can not connect to database");
          }
        return jdbcTemplate.queryForObject(sql, Double.class, amount, accountToId);

    }

    @Override
    public double subtractFromBalance(double amount, int accountFromId){
        String sql = "UPDATE accounts SET balance = balance - ?  WHERE account_id = ? RETURNING balance;";
        try {
            return jdbcTemplate.queryForObject(sql, Double.class, amount, accountFromId);
        } catch(ResourceAccessException re){
            System.out.println("Can not connect to database");
        }
        return jdbcTemplate.queryForObject(sql, Double.class, amount, accountFromId);
    }


    private Accounts mapRowToAccount(SqlRowSet accountInfo){
        Accounts account = new Accounts();
        account.setBalance(accountInfo.getBigDecimal("balance"));
        account.setAccountId(accountInfo.getInt("account_id"));
        account.setUserId(accountInfo.getInt("user_id"));
        return account;
    }

    public int getAccountIdByUser(int userId){
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?";
        int id = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return id;
    }

}
