package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDetail;
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

    @Override
    public List<TransferDetail> getTransferDetails(String username){
        List<TransferDetail> transferDetails = new ArrayList<TransferDetail>();
        String sql = "SELECT t1.transfer_id," +
                    "t1.amount," +
                    " userFrom.user_id AS user_from_id, userFrom.username AS user_from_username," +
                    " userTo.user_id AS user_to_id, userTo.username AS user_to_username," +
                    " ts.transfer_status_id, ts.transfer_status_desc," +
                    " tt.transfer_type_id, tt.transfer_type_desc" +
                    " FROM transfers AS t1" +
                    " JOIN accounts AS acctFrom ON t1.account_from = acctFrom.account_id" +
                    " JOIN users AS userFrom ON acctFrom.user_id = userFrom.user_id" +
                    " JOIN accounts AS acctTo ON t1.account_to = acctTo.account_id" +
                    " JOIN users AS userTo ON acctTo.user_id = userTo.user_id" +
                    " JOIN transfer_statuses AS ts ON t1.transfer_status_id = ts.transfer_status_id" +
                    " JOIN transfer_types AS tt ON t1.transfer_type_id = tt.transfer_type_id" +
                    " WHERE (userFrom.username, userTo.username) IN ((SELECT userFrom.username, userTo.username FROM users WHERE username ILIKE ?));";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()){
            TransferDetail transferDetail = mapRowToTransferDetail(results);
            transferDetails.add(transferDetail);
        }
        return transferDetails;
    }
        //TODO: Implement a BEGIN TRANSACTION for creating transfer, debit, and credit.
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

    //TODO: Validation of currentUser server-side:
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

    private TransferDetail mapRowToTransferDetail(SqlRowSet rs){
        TransferDetail transferDetail = new TransferDetail();
        transferDetail.setTransferId(rs.getInt("transfer_id"));
        transferDetail.setTransferTypeId(rs.getInt("transfer_type_id"));
        transferDetail.setTransferTypeDesc(rs.getString("transfer_type_desc"));
        transferDetail.setTransferStatusId(rs.getInt("transfer_status_id"));
        transferDetail.setTransferStatusDesc(rs.getString("transfer_status_desc"));
        transferDetail.setAmount(rs.getDouble("amount"));
        transferDetail.setUserFromUsername(rs.getString("user_from_username"));
        transferDetail.setUserFromId(rs.getInt("user_from_id"));
        transferDetail.setUserToUsername(rs.getString("user_to_username"));
        transferDetail.setUserToId(rs.getInt("user_to_id"));

        return transferDetail;
    }

}
