package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers {

    private Long transferId;
    private Long transferTypeId;
    private Long transferStatusId;
    private Long accountFromId;
    private Long accountToId;
    private BigDecimal amount;
    private String userFrom;
    private String userTo;
    private String transferStatusDesc;
    private String transferTypeDesc;





    //getters
    public Long getTransferId() {
        return transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUserFrom() { return userFrom; }

    public String getUserTo() { return userTo; }

    public String getTransferStatusDesc() { return transferStatusDesc; }

    public String getTransferTypeDesc() { return transferTypeDesc; }



    //setters

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public void setAccountFromId(Long accountFrom) {
        this.accountFromId = accountFrom;
    }

    public void setAccountToId(Long accountTo) {
        this.accountToId = accountTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setUserFrom(String userFrom) { this.userFrom = userFrom; }

    public void setUserTo(String userTo) { this.userTo = userTo; }

    public void setTransferStatusDesc(String transferStatusDesc) { this.transferStatusDesc = transferStatusDesc; }

    public void setTransferTypeDesc(String transferTypeDesc) { this.transferTypeDesc = transferTypeDesc; }

}
