package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.AccountNotFoundException;
import com.techelevator.tenmo.dao.BalanceException;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("account")
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao accountDao) {
        this.dao = accountDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public double getBalance(Principal user) throws BalanceException {
        return dao.getBalance(user.getName());
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Account getAccount(Principal user) throws javax.security.auth.login.AccountNotFoundException {
        return dao.getAccount(user.getName());
    }

    @RequestMapping(path= "/{userId}", method = RequestMethod.POST)
    public Integer getAccountIdByUserId(@PathVariable Integer userId) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException {
       return dao.getAccountIdByUserId(userId);
    }

}
