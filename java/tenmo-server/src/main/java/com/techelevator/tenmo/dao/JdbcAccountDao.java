package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getBalance(String username) throws BalanceException {
        String sql = "SELECT a.account_id" +
                    ", a.user_id" +
                    ", a.balance" +
                    " FROM accounts AS a" +
                    " JOIN users AS u" +
                    " ON a.user_id = u.user_id" +
                    " WHERE username ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return mapRowToAccount(results).getBalance();
        }
        throw new BalanceException("User " + username + " balance not found.");
    }

    @Override
    public Integer getAccountIdByUserId(Integer userId) throws AccountNotFoundException {
        String sql = "SELECT account_id, user_id, balance" +
                    " FROM accounts" +
                    " WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if(results.next()){
            return mapRowToAccount(results).getAccountId();
            //TODO: See if method returns correct account Id


        }
        throw new AccountNotFoundException("Account for " + userId +" not found.");
    }

    @Override
    public Account getAccount(String username) throws AccountNotFoundException {
        String sql = " SELECT a.account_id" +
                    " , a.user_id" +
                    " , a.balance" +
                    " FROM accounts AS a" +
                    " JOIN users AS u" +
                    " ON a.user_id = u.user_id" +
                    " WHERE u.username ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return mapRowToAccount(results);
        }
        throw new AccountNotFoundException("Account for " + username +" not found.");
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }



}
