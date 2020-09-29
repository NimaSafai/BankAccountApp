package com.company;

public abstract class Account implements InterestBaseRate {
    protected String name;
    protected String personnummer;
    protected double balance;
    private static int index = 10000;
    protected String accountNumber;
    protected double rate;

    //ACCOUNT CONSTRUCTOR
    public Account(String name, String personnummer, double initialDeposit) {
        this.name = name;
        this.personnummer = personnummer;
        this.accountNumber = setAccountNumber();
        balance = initialDeposit;

        index++;
        setRate();
    }

    //GETTERS
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getPN() {
        return personnummer;
    }

    public double getBalance() { return balance; }

    //SETTERS
    public abstract void setRate();

        //Varje konto får ett unikt account number baserat på det inskrivna personnumret, samt unikt ID och random nummer
    public String setAccountNumber() {
        String lastTwoOfPersonnummer = personnummer.substring(personnummer.length()-2);
        int uniqueID = index;
        int randomNumber = (int) (Math.random() * Math.pow(10, 3));
        return lastTwoOfPersonnummer + "-" + uniqueID + "-" + randomNumber;
    }

    public void deposit(double amount) throws InterruptedException {
        balance = balance + amount;
        System.out.println("\nDEPOSITING $" + amount + "...");
        Thread.sleep(1200);
    }

    public void withdraw(double amount) throws InterruptedException {
        balance = balance - amount;
        System.out.println("\nWITHDRAWING $" + amount + "...");
        Thread.sleep(1200);
    }

    //SHOW ACCOUNT INFO
    public void showInfo() {
        System.out.println(
                "NAME: " + name +
                "\nACCOUNT NUMBER: " + accountNumber +
                "\nBALANCE: " + balance +
                "\nRATE: " + rate + "%"
                );
    }
    public abstract String toString();
}