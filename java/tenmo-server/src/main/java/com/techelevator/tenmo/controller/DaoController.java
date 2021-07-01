package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class DaoController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    public DaoController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "accounts/{userId}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int userId) {
        BigDecimal balance = accountDao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path = "accounts/{userId}/transfers", method = RequestMethod.GET)
    public List<Transfers> getAllTransfers(@PathVariable int userId){
        List<Transfers> listTransfers = transferDao.getAllTransfers(userId);
        return listTransfers;
    }

}
