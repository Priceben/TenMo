package com.techelevator.tenmo.dao;

import java.util.List;
import java.math.BigDecimal;
import com.techelevator.tenmo.model.Transfers;


public interface TransferDao {


   public List<Transfers> getAllTransfers(Long userId);

   public Transfers getTransferById(Long transferId);
   public Transfers sendTransfer(Long userFrom, Long userTo, BigDecimal amount);


}
