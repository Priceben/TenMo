package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class DaoController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    public DaoController(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "accounts/{userId}/balance", method = RequestMethod.GET)
    public double getBalance(@PathVariable int userId) {
        double balance = accountDao.getBalance(accountDao.getAccountIdByUser(userId));
        return balance;
    }

    @RequestMapping(path = "accounts/transfers/all", method = RequestMethod.GET)
    public List<Transfers> getAllTransfers(Integer userId){
        List<Transfers> listTransfers = transferDao.getAllTransfers(userId);
        return listTransfers;
    }

    @RequestMapping(path = "accounts/transfers", method = RequestMethod.POST)
    public Transfers sendMoney(@RequestBody Transfers transfer){

        int userFrom= transfer.getAccountFromId();
        int userTo = transfer.getAccountToId();
        double amount = transfer.getAmount();

        Transfers processedTransfer = transferDao.sendTransfer(userFrom, userTo, amount);

        System.out.println("Transfer successful!");
        return processedTransfer;
    }
    @RequestMapping(value = "/accounts/{userId}", method = RequestMethod.GET)
    public int accountId(@Valid @PathVariable int userId) {
        int accountId = accountDao.getAccountIdByUser(userId);
        return accountId;
    }

}
