package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.SendMoneyToSelfException;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;
    private TransferDao transferDao;
    private AccountDao accountDao;

    @Override
    public List<Transfers> getAllTransfers(int userId) {
        List<Transfers> listTransfers = new ArrayList<>();
        String sql = "SELECT t.*, x.username AS user_to, y.username AS user_from" +
                "FROM transfers t INNER JOIN accounts a ON t.account_to = a.account_id" +
                "INNER JOIN accounts b ON t.account_from = b.account_id" +
                "INNER JOIN user x ON x.user_id = a.user_id" +
                "INNER JOIN user y ON y.user_id = b.user_id" +
                "WHERE a.user_id = ? OR b.user_id = ?;";
        SqlRowSet transferInfo = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(transferInfo.next()){
            Transfers transfer = mapRowToTransfers(transferInfo);
            listTransfers.add(transfer);
        }
        return listTransfers;
    }

    @Override
    public Transfers getTransferById(int transferId){
        return null;
    }

    @Override
    public Transfers sendTransfer(int userFrom, int userTo, BigDecimal amount, int transferTypeId){
        transferTypeId = 2;
        Transfers newTransfer = new Transfers();
            if (userTo == userFrom) {
                System.out.println("You can't send money to yourself!!!");
            }
            if(amount.compareTo(accountDao.getBalance(userFrom)) == -1 || amount.compareTo(accountDao.getBalance(userFrom)) == -0){
                String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                        "Values(2, 2, ?, ?, ?);" +
                        "";
                jdbcTemplate.update(sql, userFrom, userTo, amount);
            }


        return null;
    }

    private Transfers mapRowToTransfers(SqlRowSet transferInfo){
        Transfers transfer = new Transfers();
        transfer.setAccountFrom(transferInfo.getInt("account_from"));
        transfer.setAccountTo(transferInfo.getInt("account_to"));
        transfer.setTransferId(transferInfo.getInt("transfer_id"));
        transfer.setTransferStatusId(transferInfo.getInt("transfer_status_id"));
        transfer.setAmount(transferInfo.getBigDecimal("amount"));
        transfer.setTransferTypeId(transferInfo.getInt("transfer_type_id"));

        return transfer;
    }
}
