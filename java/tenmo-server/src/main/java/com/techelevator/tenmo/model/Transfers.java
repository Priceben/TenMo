package com.techelevator.tenmo.model;

public class Transfers {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFromId;
    private int accountToId;
    private double amount;
    private String userFrom;
    private String userTo;
    private String transferStatusDesc;
    private String transferTypeDesc;





    //getters
    public int getTransferId() {
        return transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public double getAmount() {
        return amount;
    }

    public String getUserFrom() { return userFrom; }

    public String getUserTo() { return userTo; }

    public String getTransferStatusDesc() { return transferStatusDesc; }

    public String getTransferTypeDesc() { return transferTypeDesc; }



    //setters

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public void setAccountFromId(int accountFrom) {
        this.accountFromId = accountFrom;
    }

    public void setAccountToId(int accountTo) {
        this.accountToId = accountTo;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUserFrom(String userFrom) { this.userFrom = userFrom; }

    public void setUserTo(String userTo) { this.userTo = userTo; }

    public void setTransferStatusDesc(String transferStatusDesc) { this.transferStatusDesc = transferStatusDesc; }

    public void setTransferTypeDesc(String transferTypeDesc) { this.transferTypeDesc = transferTypeDesc; }

}
