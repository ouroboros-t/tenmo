package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDetail;
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
    //TODO: CLEANUP:: THIS METHOD BELOW IS NOT USED:::
//@RequestMapping(path = "", method = RequestMethod.GET)
//    public List<Transfer> findTransfers(Principal user){
//        return dao.getUserTransfers(user.getName());
//}
    //TODO: CLEANUP:: THIS METHOD ABOVE IS NOT USED:::


@RequestMapping(path="", method = RequestMethod.GET)
    public List<TransferDetail> getTransferDetails(Principal user){
        return dao.getTransferDetails(user.getName());

}


}
