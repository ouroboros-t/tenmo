package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferDao  implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

   @Override
    public Transfer createTransfer(Transfer transfer){
       String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
               " VALUES(?,?,?,?,?) RETURNING transfer_id;";
       Long newId = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
               transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getAmount());
       transfer.setTransferId(newId);
       return transfer;
   }


}
