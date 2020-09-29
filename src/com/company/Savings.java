package com.company;

public class Savings extends Account {
    private int safetyDepositBoxID;
    private int safetyDepositBoxKey;

    //CONSTRUCTOR SAVINGS ACCOUNT
    public Savings(String name, String personnummer, double initalDeposit) {
        super(name, personnummer, initalDeposit);
        //Savings-konto identifieras med en 1:a i början av account number
        accountNumber = "1-" + accountNumber;
        setSafetyDepositBox();
    }

    @Override
    public void setRate() {
        rate = getBaseRate() - .25;
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
        return String.join(",",this.name,this.personnummer,String.valueOf(this.balance),"Savings");
    }
}