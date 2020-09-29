package com.company;

public class Checking extends Account {
    private int debitCardNumber;
    private int debitCardPIN;

    //CONSTRUCTOR CHECKING ACCOUNT
    public Checking(String name, String personnummer, double initalDeposit) {
        super(name, personnummer, initalDeposit);
        accountNumber = "2-" + accountNumber;                                   //Checking-konto identifieras med en 2:a i början av account number
        setDebitCard();
    }

    @Override
    public void setRate() {
        rate = getBaseRate() * .25;
    }

    //Checkings-konton får ett debitcard med ett unikt kortnummer och PIN-kod
    private void setDebitCard() {
        debitCardNumber = (int) (Math.random() * Math.pow(10,12));
        debitCardPIN = (int) (Math.random() * Math.pow(10, 4));
    }

    public void showInfo() {
        super.showInfo();
        System.out.println(
                "~~~ Your checking account features ~~~" +
                "\n • Debit card number: " + debitCardNumber +
                "\n • Debit card PIN: " + debitCardPIN
                );
    }

    @Override
    public String toString() {
        return String.join(",",this.name,this.personnummer,String.valueOf(this.balance),"Checking");
    }
}