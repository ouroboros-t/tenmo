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

    //TODO: CLEANUP:: THIS METHOD BELOW IS NOT USED:::
    public List<Transfer> getUserTransfers(String username){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount, " +
                "a.account_id, a.user_id, a.balance, u.user_id, u.username" +
                    " FROM transfers AS t" +
                    " JOIN accounts AS a ON t.account_from = a.account_id" +
                    " JOIN users AS u ON a.user_id = u.user_id WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }
    //TODO: CLEANUP:: THIS METHOD ABOVE IS NOT USED:::
    @Override
    public List<TransferDetail> getTransferDetails(String username){
        List<TransferDetail> transferDetails = new ArrayList<TransferDetail>();
        String sql = "SELECT t.transfer_id, t.account_from, t.account_to, t.amount," +
                    " t.transfer_status_id, ts.transfer_status_desc, " +
                    " t.transfer_type_id, tt.transfer_type_desc," +
                    " a1.account_id, a1.balance," +
                    " u.user_id, u.username" +
                    " FROM transfers AS t" +
                    " JOIN accounts AS a1 ON t.account_from = a1.account_id" +
                    " JOIN users AS u ON a1.user_id = u.user_id" +
                    " JOIN accounts AS a2 ON a2.user_id = u.user_id" +
                    " JOIN transfer_statuses AS ts ON t.transfer_status_id = ts.transfer_status_id" +
                    " JOIN transfer_types AS tt ON t.transfer_type_id = tt.transfer_type_id WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()){
            TransferDetail transferDetail = mapRowToTransferDetail(results);
            transferDetails.add(transferDetail);
        }
        return transferDetails;
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

    private TransferDetail mapRowToTransferDetail(SqlRowSet rs){
        TransferDetail transferDetail = new TransferDetail();
        transferDetail.setTransferId(rs.getInt("transfer_id"));
        transferDetail.setTransferTypeId(rs.getInt("transfer_type_id"));
        transferDetail.setTransferTypeDesc(rs.getString("transfer_type_desc"));
        transferDetail.setTransferStatusId(rs.getInt("transfer_status_id"));
        transferDetail.setTransferStatusDesc(rs.getString("transfer_status_desc"));
        transferDetail.setAccountFromId(rs.getInt("account_from"));
        transferDetail.setAccountToId(rs.getInt("account_to"));
        transferDetail.setAmount(rs.getDouble("amount"));
        transferDetail.setAccountId(rs.getInt("account_id"));
        transferDetail.setBalance(rs.getDouble("balance"));
        transferDetail.setUserId(rs.getInt("user_id"));
        transferDetail.setUsername(rs.getString("username"));

        return transferDetail;
    }



}
