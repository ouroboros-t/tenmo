package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.BalanceNotFoundException;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
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

    public AccountController(AccountDao accountDao){
        this.dao = accountDao;

    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public BigDecimal getBalance(Principal user) throws BalanceNotFoundException {
        String username = user.getName();
        return dao.getBalance(username);
    }

}
