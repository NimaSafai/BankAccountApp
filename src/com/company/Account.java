package com.company;

public abstract class Account {
    public static final double BASE_RATE = 4.5;                     //Jag bara satte 4.5% som ränta på måfå
    private final Customer owner;
    private static int index = 10000;
    private double balance;
    private String accountNumber;

    //ACCOUNT CONSTRUCTOR

    public Account(Customer owner, double initialDeposit) {
        this.owner = owner;
        this.setAccountNumber(generateAccountNumber());
        balance = initialDeposit;

        index++;
    }
    //GETTERS

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return this.owner;
    }

    public abstract double getRate();

    public abstract String getType();

    //SETTERS
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    //Varje konto får ett unikt account number baserat på det inskrivna personnumret, samt unikt ID och random nummer
    public String generateAccountNumber() {
        String lastTwoOfPersonnummer = owner.getPersonnummer().toString().substring(owner.getPersonnummer().toString().length() - 2);
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
                "ACCOUNT HOLDER: " + owner.getName() +
                        "\nACCOUNT NUMBER: " + accountNumber +
                        "\nBALANCE: " + balance +
                        "\nRATE: " + this.getRate() + "%"
        );
    }

    public abstract String toString();
}