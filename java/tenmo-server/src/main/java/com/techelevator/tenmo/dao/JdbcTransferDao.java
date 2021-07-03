package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
    public Transfers sendTransfer(int accountFromId, int accountToId, double amount){
//        int userFromAccount = accountDao.getAccountIdByUser(userFrom);
//        int userToAccount = accountDao.getAccountIdByUser(userTo);

            if (accountToId == accountFromId) {
                System.out.println("You can't send money to yourself!!!");
            }
            if(amount <= accountDao.getBalance(accountFromId)){
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
        transfer.setAccountFromId(transferInfo.getInt("user_from"));
        transfer.setAccountToId(transferInfo.getInt("user_to"));
        transfer.setTransferId(transferInfo.getInt("transfer_id"));
        transfer.setTransferStatusId(transferInfo.getInt("transfer_status_id"));
        transfer.setAmount(transferInfo.getDouble("amount"));
        transfer.setTransferTypeId(transferInfo.getInt("transfer_type_id"));

        return transfer;
    }
}
