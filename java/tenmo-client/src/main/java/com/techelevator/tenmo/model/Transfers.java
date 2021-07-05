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
    private String transferTypeDesc;
    private String transferStatusDesc;


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

    public String getTransferTypeDesc() { return transferTypeDesc; }

    public String getTransferStatusDesc() { return transferStatusDesc; }


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

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUserFrom(String userFrom) { this.userFrom = userFrom; }

    public void setUserTo(String userTo) { this.userTo = userTo; }

    public void setTransferTypeDesc(String transferTypeDesc) { this.transferTypeDesc = transferTypeDesc; }

    public void setTransferStatusDesc(String transferStatusDesc) { this.transferStatusDesc = transferStatusDesc; }

}
