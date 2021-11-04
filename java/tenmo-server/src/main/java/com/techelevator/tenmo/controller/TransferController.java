package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("transfer")
public class TransferController {
    private TransferDao dao;


    public TransferController(TransferDao dao){
        this.dao = dao;
    }

    /**
     * performs the transfer
     * @param transfer
     * @return
     */
    @RequestMapping(path ="", method = RequestMethod.POST)
    public Transfer transfer(@RequestBody Transfer transfer, Principal user){
        transfer = dao.createTransfer(transfer);
        dao.debitAccountFrom(transfer, user.getName());
        return transfer;
    }

    //helper method to check to see if person requesting bucks/sending bucks own the account (From account matches)

    //private boolean validateUser

}
