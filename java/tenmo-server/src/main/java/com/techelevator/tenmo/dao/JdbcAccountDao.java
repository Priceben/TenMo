package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId){
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        return balance;
    }

    @Override
    public BigDecimal addToBalance(BigDecimal amount, int userId){
        Accounts account = findByUserId(userId);
        BigDecimal balance = account.getBalance().add(amount);
          String sql = "UPDATE accounts SET balance = (balance + ? ) WHERE user_id = ?;";
          jdbcTemplate.update(sql, balance, userId);
        return account.getBalance();
    }

    @Override
    public BigDecimal subtractFromBalance(BigDecimal amount, int userId){
        Accounts account = findByUserId(userId);
        BigDecimal balance = account.getBalance().subtract(amount);
        String sql = "UPDATE accounts SET balance = (balance - ? ) WHERE user_id = ?;";
        jdbcTemplate.update(sql, balance, userId);
        return account.getBalance();
    }

    @Override
    public Accounts findByUserId(int userId){
        Accounts account;
        String sql = "SELECT * FROM accounts WHERE user_id = ?;";
        SqlRowSet accountInfo = jdbcTemplate.queryForRowSet(sql, userId);
        account = mapRowToAccount(accountInfo);
        return account;
    }

    private Accounts mapRowToAccount(SqlRowSet accountInfo){
        Accounts account = new Accounts();
        account.setBalance(accountInfo.getBigDecimal("balance"));
        account.setAccountId(accountInfo.getInt("account_id"));
        account.setUserId(accountInfo.getInt("user_id"));
        return account;
    }

}
