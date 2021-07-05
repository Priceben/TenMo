package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public BigDecimal getBalance(@Valid @PathVariable Long userId) {
        BigDecimal balance = accountDao.getBalance(accountDao.getAccountIdByUser(userId));
        return balance;
    }

    @RequestMapping(path = "accounts/transfers/{id}", method = RequestMethod.GET)
    public List<Transfers> getAllTransfers(@Valid @PathVariable Long id){
        List<Transfers> listTransfers = transferDao.getAllTransfers(id);
        return listTransfers;
    }

    @RequestMapping(path = "accounts/transfers", method = RequestMethod.POST)
    public Transfers sendMoney(@RequestBody Transfers transfer){

        Long userFrom= transfer.getAccountFromId();
        Long userTo = transfer.getAccountToId();
        BigDecimal amount = transfer.getAmount();

        Transfers processedTransfer = transferDao.sendTransfer(userFrom, userTo, amount);

        System.out.println("Transfer successful!");
        return processedTransfer;
    }
    @RequestMapping(value = "/accounts/{userId}", method = RequestMethod.GET)
    public Long accountId(@Valid @PathVariable Long userId) {
        Long accountId = accountDao.getAccountIdByUser(userId);
        return accountId;
    }
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listUsers(){
        List<User> users = userDao.findAll();
        return users;
    }
    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfers getTransfersById(@Valid @PathVariable Long transferId){
        Transfers transfer = transferDao.getTransferById(transferId);
        return transfer;
    }

}
