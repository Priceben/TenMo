package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao){
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }



    /*@Override
    public List<Transfers> getAllTransfers(int userId) {
        List<Transfers> list = new ArrayList<>();
        String sql = "SELECT t.transfer_id, 'To: '||u.username AS username, t.amount"
                +" FROM transfers t"
                +" INNER JOIN accounts a ON t.account_to = a.account_id"
                +" INNER JOIN users u ON a.user_id = u.user_id"
                +" WHERE t.account_from = ?"
                +" UNION"
                +" SELECT t.transfer_id, 'From: '||u.username AS username, t.amount"
                +" FROM transfers t"
                +" INNER JOIN accounts a ON t.account_from = a.account_id"
                +" INNER JOIN users u ON a.user_id = u.user_id"
                +" WHERE t.account_to = ?"
                +" ORDER BY transfer_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next() ) {
            Transfers transfers = mapRowTorTransfer(results);
            list.add(transfers);
        }
        return list;
    }*/

    @Override
    public List<Transfers> getAllTransfers(Long accountId) {
        List<Transfers> list = new ArrayList<>();
        String sql = "SELECT t.transfer_id, 'To: ' || u.username AS username, t.amount"
                +" FROM transfers t"
                +" INNER JOIN accounts a ON t.account_to = a.account_id"
                +" INNER JOIN users u ON a.user_id = u.user_id"
                +" WHERE t.account_from = ?"
                +" UNION"
                +" SELECT t.transfer_id, 'From: ' || u.username AS username, t.amount"
                +" FROM transfers t"
                +" INNER JOIN accounts a ON t.account_from = a.account_id"
                +" INNER JOIN users u ON a.user_id = u.user_id"
                +" WHERE t.account_to = ?"
                +" ORDER BY transfer_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while (results.next() ) {
            Transfers transfers = mapRowForTransferList(results);
            list.add(transfers);
        }
        return list;
    }

    @Override
    public Transfers getTransferById(Long transferId){
        Transfers transfer = new Transfers();
        String sql = "SELECT t.transfer_id, t.amount, tt.transfer_type_desc, ts.transfer_status_desc, ua.username AS user_from, ub.username AS user_to " +
                "FROM transfers t " +
                "INNER JOIN transfer_statuses ts " +
                "ON ts.transfer_status_id = t.transfer_status_id " +
                "INNER JOIN transfer_types tt " +
                "ON t.transfer_type_id = tt.transfer_type_id " +
                "INNER JOIN accounts a ON t.account_from = a.account_id " +
                "INNER JOIN accounts b ON t.account_to = b.account_id " +
                "INNER JOIN users ua ON ua.user_id = a.user_id " +
                "INNER JOIN users ub ON ub.user_id = b.user_id " +
                "WHERE t.transfer_id = ?;";
        try {
            //if this doesn't work, try SQLrowset and MapToTransfer
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            while(results.next()){
                transfer = mapRowToTransfers(results);
            }
            return transfer;
        }catch(ResourceAccessException rae){
            System.out.println("Cannot connect with Server!!!");
        }
        return transfer;
    }

    @Override
    public Transfers sendTransfer(Long accountFromId, Long accountToId, BigDecimal amount){

            if (accountToId == accountFromId) {
                System.out.println("You can't send money to yourself!!!");
            }
            if(amount.compareTo(accountDao.getBalance(accountFromId)) == 0 || amount.compareTo(accountDao.getBalance(accountFromId)) == -1) {
                String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                        " Values(2, 2, ?, ?, ?);";
                jdbcTemplate.update(sql, accountFromId, accountToId, amount);
                accountDao.addToBalance(amount, accountToId);
                accountDao.subtractFromBalance(amount, accountFromId);
                System.out.println("Transfer completed!");
            }
            else{
                System.out.println("Transfer failed due to insufficient funds");
            }
            return null;
    }

    private Transfers mapRowToTransfers(SqlRowSet transferInfo){
        Transfers transfer = new Transfers();
        transfer.setTransferId(transferInfo.getLong("transfer_id"));
        transfer.setAmount(transferInfo.getBigDecimal("amount"));
        transfer.setTransferTypeDesc(transferInfo.getString("transfer_type_desc"));
        transfer.setTransferStatusDesc(transferInfo.getString("transfer_status_desc"));
        transfer.setUserTo(transferInfo.getString("user_from"));
        transfer.setUserFrom(transferInfo.getString("user_to"));

        return transfer;
    }

    private Transfers mapRowForTransferList(SqlRowSet results){
        Transfers transfer = new Transfers();
        transfer.setTransferId(results.getLong("transfer_id"));
        transfer.setUserFrom(results.getString("username"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }

}
