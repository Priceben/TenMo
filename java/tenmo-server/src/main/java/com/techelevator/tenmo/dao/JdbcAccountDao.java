package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal getBalance(int userId){
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        return balance;
    }

    @Override
    public BigDecimal addToBalance(BigDecimal amount, int accountId){
        Accounts account = findByAccountId(accountId);
        BigDecimal balance = account.getBalance().add(amount);
        //String sql = "UPDATE accounts"
        return account.getBalance();
        //TODO flesh out method
    }

    @Override
    public BigDecimal subtractFromBalance(BigDecimal amount, int accountId){
        return null;
        //TODO flesh out method
    }

    @Override
    public Accounts findByUserId(int userId){
        return null;
        //TODO flesh out method
    }

    @Override
    public Accounts findByAccountId(int accountId){
        Accounts account;
        String sql = "SELECT * FROM accounts WHERE account_id = ?;";
        SqlRowSet accountInfo = jdbcTemplate.queryForRowSet(sql, accountId);
        return null;
        //TODO flesh out method
    }

    private Accounts mapRowToAccount(SqlRowSet accountInfo){
        Accounts account = new Accounts();
        account.setBalance(accountInfo.getBigDecimal("balance"));
        account.setAccountId(accountInfo.getInt("account_id"));
        account.setUserId(accountInfo.getInt("user_id"));
        return account;
    }

}
