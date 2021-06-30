package com.techelevator.tenmo.dao;

import java.util.List;
import java.math.BigDecimal;
import com.techelevator.tenmo.model.Transfers;


public interface TransferDao {


   public List<Transfers> getAllTransfers(int userId);

   public Transfers getTransferById(int transferId);
   public String sendTransfer(int userFrom, int userTo,BigDecimal amount);

}
