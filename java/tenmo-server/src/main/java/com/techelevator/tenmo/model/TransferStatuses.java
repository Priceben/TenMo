package com.techelevator.tenmo.model;

public class TransferStatuses {

    private Long transferStatusId;
    private String transferStatusDesc;

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}
