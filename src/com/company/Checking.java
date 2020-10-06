package com.company;

public class Checking extends Account {
    public static final String ACCOUNT_TYPE = "CHECKING";
    private int debitCardNumber;
    private int debitCardPIN;

    //CONSTRUCTOR CHECKING ACCOUNT
    public Checking(Customer owner, double initalDeposit) {
        super(owner, initalDeposit);
        this.setAccountNumber("2-" + this.getAccountNumber()); //Checking-konto identifieras med en 2:a i början av account number
        setDebitCard();
    }

    @Override
    public double getRate() {
        return Account.BASE_RATE * .25;
    }

    public String getType() {
        return Checking.ACCOUNT_TYPE;
    }

    //Checkings-konton får ett debitcard med ett unikt kortnummer och PIN-kod
    private void setDebitCard() {
        debitCardNumber = (int) (Math.random() * Math.pow(10, 12));
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
        return String.join(",",
                this.getOwner().getName(),
                this.getOwner().getPersonnummer().toString(),
                Checking.ACCOUNT_TYPE,
                String.valueOf(this.getBalance()));
    }
}