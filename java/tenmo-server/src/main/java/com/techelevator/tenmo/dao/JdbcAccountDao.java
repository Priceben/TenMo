package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(Long accountId){
        String sql = "SELECT balance FROM accounts WHERE account_id = ?;";
        try {
            BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
            return balance;
        } catch (RestClientException rce){
            System.out.println("Invalid User Id");
        }BigDecimal zero = new BigDecimal("0.00");
        return zero;
    }

    @Override
    public BigDecimal addToBalance(BigDecimal amount, Long accountToId){
          String sql = "UPDATE accounts SET balance = balance + ?  WHERE account_id = ? RETURNING balance;";
          try {
              return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, accountToId);
          } catch(ResourceAccessException re){
              System.out.println("Can not connect to database");
          }
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, accountToId);

    }

    @Override
    public BigDecimal subtractFromBalance(BigDecimal amount, Long accountFromId){
        String sql = "UPDATE accounts SET balance = balance - ?  WHERE account_id = ? RETURNING balance;";
        try {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, accountFromId);
        } catch(ResourceAccessException re){
            System.out.println("Can not connect to database");
        }
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, amount, accountFromId);
    }


    private Accounts mapRowToAccount(SqlRowSet accountInfo){
        Accounts account = new Accounts();
        account.setBalance(accountInfo.getBigDecimal("balance"));
        account.setAccountId(accountInfo.getInt("account_id"));
        account.setUserId(accountInfo.getInt("user_id"));
        return account;
    }

    public Long getAccountIdByUser(Long userId){
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, userId);
        return id;
    }

}
