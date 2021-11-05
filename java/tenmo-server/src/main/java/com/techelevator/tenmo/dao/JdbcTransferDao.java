package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


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

    //TODO: SELECT enough information so that the username can also be retrieved?
    @Override
    public List<Transfer> getUserTransfers(){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to,amount" +
                    " FROM transfers;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
        }
        return transfers;
    }



    @Override
    public boolean accountTransaction(Transfer transfer) {
            //debit
            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?;";
            jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFromId());

            //credit
            sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?;";
            jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountToId());

            return true;
            //we had a validation method here originally, so we're leaving it as returning a boolean.
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

        return accountFromId.equals(transfer.getAccountFromId());
    }

    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();

        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFromId(rs.getInt("account_from"));
        transfer.setAccountToId(rs.getInt("account_to"));
        transfer.setAmount(rs.getDouble("amount"));

        return transfer;
    }


}
