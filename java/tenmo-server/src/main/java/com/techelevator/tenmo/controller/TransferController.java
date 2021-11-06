package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        Transfer newTransfer = dao.createTransfer(transfer);
        dao.accountTransaction(newTransfer);
        return newTransfer;
    }

@RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> findTransfers(Principal user){
        return dao.getUserTransfers(user.getName());
}


}
