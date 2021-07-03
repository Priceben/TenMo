package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfers {
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFromId;
    private int accountToId;
    private double amount;

    public Transfers(int accountFrom, int accountTo, double amount){
        this.accountFromId = accountFrom;
        this.accountToId = accountTo;
        this.amount = amount;
    }

    public Transfers( int transferId, int accountFrom, int accountTo, double amount){
        this.transferId = transferId;
        this.accountFromId = accountFrom;
        this.accountToId = accountTo;
        this.amount = amount;
    }
    public Transfers(int transferStatusId, int transferTypeId, int accountFrom, int accountTo, double amount){
        this.accountFromId = accountFrom;
        this.accountToId = accountTo;
        this.amount = amount;
        this.transferStatusId = transferStatusId;
        this.transferTypeId = transferTypeId;
    }


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
}
