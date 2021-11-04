package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.AccountNotFoundException;
import com.techelevator.tenmo.dao.BalanceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public BigDecimal getBalance(Principal user) throws BalanceException {
        return dao.getBalance(user.getName());
    }

    @RequestMapping(path= "/{userId}", method = RequestMethod.GET)
    public Long getAccountId(@PathVariable Long userId) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException {
       return dao.getAccountIdByUserId(userId);
    }

}
