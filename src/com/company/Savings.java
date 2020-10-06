package com.company;

public class Savings extends Account {
    public static final String ACCOUNT_TYPE = "SAVINGS";
    private int safetyDepositBoxID;
    private int safetyDepositBoxKey;

    //CONSTRUCTOR SAVINGS ACCOUNT
    public Savings(Customer owner, double initalDeposit) {
        super(owner, initalDeposit);
        //Savings-konto identifieras med en 1:a i början av account number
        this.setAccountNumber("1-" + this.getAccountNumber());
        setSafetyDepositBox();
    }

    @Override
    public double getRate() {
        return Account.BASE_RATE - .25;
    }

    @Override
    public String getType() {
        return Savings.ACCOUNT_TYPE;
    }

    //Savings-konton får en deposit box med ett unikt ID och key
    private void setSafetyDepositBox() {
        safetyDepositBoxID = (int) (Math.random() * Math.pow(10, 3));
        safetyDepositBoxKey = (int) (Math.random() * Math.pow(10, 4));
    }

    public void showInfo() {
        super.showInfo();
        System.out.println(
                "~~~ Your savings account features ~~~" +
                        "\n • Safety deposit box ID: " + safetyDepositBoxID +
                        "\n • Safety deposit box Key: " + safetyDepositBoxKey
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