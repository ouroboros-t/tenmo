package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


@Component
public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                " VALUES(?, ?, ?, ?, ?) RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFromId(),
               transfer.getAccountToId(), transfer.getAmount());
        transfer.setTransferId(newId);
        return transfer;
    }

    @Override
    public boolean debitAccountFrom(Transfer transfer) {
            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?;";
            jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFromId());
            return true;
    }

    public boolean validateAccountFrom(Transfer transfer, String username) {
        String sql = "SELECT t.account_from" +
                    " FROM transfers AS t" +
                    " JOIN accounts AS a" +
                    " ON t.account_from = a.account_id" +
                    " JOIN users AS u" +
                    " ON a.user_id = u.user_id" +
                    " WHERE username ILIKE ?;";
        Integer accountFromId = jdbcTemplate.queryForObject(sql, Integer.class, username);
        //TODO: See if accountID is correct (2000s)
        return accountFromId.equals(transfer.getAccountFromId());
    }


}
