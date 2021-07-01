package com.techelevator.tenmo.model;

public class SendMoneyToSelfException extends Exception{
    private static final long serialVersionUID = 21L;

    public SendMoneyToSelfException(String message){
        super(message);
        System.out.println("You can't send money to yourself!!!!!");
    }
}
