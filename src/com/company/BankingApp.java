package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class BankingApp {

    private  ArrayList<Account> accounts;

    public void run() throws InterruptedException {

        Scanner scan = new Scanner(System.in);
        boolean loop = true;
        accounts = new ArrayList<>();

        System.out.println("\n~~~~~ WELCOME TO THE BANKING PROGRAM™ ~~~~~");
        do {
            System.out.println("\nPLEASE SELECT FROM THE FOLLOWING CHOICES:");
            System.out.println("   1. Open a new account");
            System.out.println("   2. See info for existing customer");
            System.out.println("   3. Deposit funds");
            System.out.println("   4. Withdraw funds");
            System.out.println("   5. Transfer funds");
            System.out.println("   6. Add customer info from datafile (.csv)");
            System.out.println("   0. Exit program");

            String menuChoice = scan.nextLine();
            Account account = null;

            switch (menuChoice) {

                //OPEN ACCOUNT
                case "1":
                    System.out.println("Please enter the full name of the account holder: ");
                    String name = scan.nextLine();

                    System.out.println("Please enter the personnummer (10 digits) of the account holder: ");
                    String personnummer = scan.nextLine();

                    System.out.println("Please enter your initial deposit:");
                    double initialDeposit = scan.nextDouble();
                    scan.nextLine();

                    System.out.println("Please enter if you would like to open a Savings account or a Checking account: ");
                    String accountType = scan.nextLine();
                    if (accountType.equalsIgnoreCase("Savings")) {
                        account = new Savings(name, personnummer, initialDeposit);
                        System.out.println("A Savings account has successfully been created for " + name + ", " + personnummer + " and with the initial deposit of $" + initialDeposit + "\n");
                        account.showInfo();
                        accounts.add(account);
                    } else if (accountType.equalsIgnoreCase("Checking")) {
                        account = new Checking(name, personnummer, initialDeposit);
                        System.out.println("\nA Checking account has successfully been created for " + name + ", " + personnummer + " and with the initial deposit of $" + initialDeposit + "0.\n");
                        account.showInfo();
                        accounts.add(account);
                    }
                    break;

                //SHOW INFO
                case "2":
                    System.out.println("\nPlease choose identifier to view account info:");
                    System.out.println("     A. Account holder name");
                    System.out.println("     B. Account holder personnummer");
                    System.out.println("     C. Account number");

                    String infoChoice = scan.nextLine().toUpperCase();

                    switch (infoChoice) {
                        case "A":
                            System.out.println("\nEnter the full name of the account holder: ");
                            name = scan.nextLine();

                            account = findAccountName(name);
                            if (account.getName().equals(name)) {
                                account.showInfo();
                            } else { System.out.println("ERROR - Account not found"); }
                            break;

                        case "B":
                            System.out.println("\nEnter the personnummer of the account holder: ");
                            personnummer = scan.nextLine();

                            account = findAccountPN(personnummer);
                            if (account.getPN().equals(personnummer)) {
                                account.showInfo();
                            } else { System.out.println("ERROR - Account not found"); }
                            break;

                        case "C":
                            System.out.println("\nEnter account number (11 numbers with dashes): ");
                            String accountNumber = scan.nextLine();

                            account = findAccountNumber(accountNumber);
                            if (account.getAccountNumber().equals(accountNumber)) {
                                account.showInfo();
                            } else { System.out.println("ERROR - Account not found"); }
                            break;
                    }
                    break;

                //DEPOSIT
                case "3":
                    System.out.println("\nPlease type in the account number that you would like to deposit funds to: ");
                    String accountNumber = scan.nextLine();

                    System.out.println("\nHow much would you like to deposit?");
                    double depositFunds = scan.nextInt();
                    scan.nextLine();

                    account = findAccountNumber(accountNumber);
                        if(account != null) {
                            account.deposit(depositFunds);
                            account.showInfo();
                        }
                        else {
                            System.out.println("ERROR - Account not found");
                        }
                    break;

                //WITHDRAW
                case "4":
                    System.out.println("\nPlease type in the account number that you would like to withdraw funds from: ");
                    accountNumber = scan.nextLine();

                    System.out.println("\nHow much would you like to withdraw?");
                    double withdrawFunds = scan.nextInt();
                    scan.nextLine();

                    account = findAccountNumber(accountNumber);
                    if (account != null) {
                        if (withdrawFunds < account.balance) {
                            account.withdraw(withdrawFunds);
                            System.out.println("Account number " + account.getAccountNumber() + " now has the balance $" + account.getBalance());
                        } else {
                            System.out.println("\nChecking account...");
                            Thread.sleep(1200);
                            System.out.println("\nYou do not have enough funds in your account. Please try again!");
                        }
                    } else { System.out.println("ERROR - Account not found. Please try again!"); }
                    break;


                //TRANSFER
                case "5":
                    System.out.println("\nPlease type in the account number that you would like to transfer funds from: ");
                    String fromAccount = scan.nextLine();

                    Account sendAccount = findAccountNumber(fromAccount);
                    if(sendAccount == null) { System.out.println("ERROR - Account not found"); }

                    System.out.println("\nHow much would you like to transfer?");
                    double transferAmount = scan.nextInt();
                    scan.nextLine();

                    System.out.println("\nPlease type in the account number that you would like to transfer funds to: ");
                    String toAccount = scan.nextLine();

                    Account receiveAccount = findAccountNumber(toAccount);
                    if(receiveAccount == null) { System.out.println("ERROR - Account not found"); }

                    if(sendAccount != null && receiveAccount != null) {
                        sendAccount.withdraw(transferAmount);
                        receiveAccount.deposit(transferAmount);
                    }
                    System.out.println("\nCompleting transfer, please wait...");
                    Thread.sleep(2000);
                    System.out.println("\nThe transfer of $" + transferAmount + " from account number " + fromAccount + " to account number " + toAccount + " has now been completed!\n");

                    System.out.println("\n*************************************");
                    sendAccount.showInfo();
                    System.out.println("*************************************");
                    receiveAccount.showInfo();
                    System.out.println("*************************************\n");
                    break;

                //READ CSV
                case "6":
                    System.out.println("\nPlease enter the full directory path to the datafile you would like to upload (only .csv)");
                    String file = scan.nextLine();                         //Min exempelfil: "/Users/Nima/Desktop/NewBankAccounts.csv"

                    ArrayList<String[]> newAccountHolders = (ArrayList<String[]>) csvReader.read(file);
                    for (String[] accountHolder : newAccountHolders) {
                        name = accountHolder[0];
                        personnummer = accountHolder[1];
                        accountType = accountHolder[2];
                        initialDeposit = Double.parseDouble(accountHolder[3]);
                        if (accountType.equals("Savings")) {
                            accounts.add(new Savings(name, personnummer, initialDeposit));
                        } else if (accountType.equals("Checking")) {
                            accounts.add(new Checking(name, personnummer, initialDeposit));
                        }
                    }
                    for (Account acc : accounts) {
                        acc.showInfo();
                        System.out.println("\n**************\n");
                    }
                    break;

                //EXIT
                case "0":
                    System.out.println("\nThank you for using The Banking Program™. Goodbye!");
                    loop = false;
                    break;

                default:
                    System.out.println("\nSomething went wrong - please try again!");
                    break;
            }
        } while (loop);
    }

    private Account findAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account; }
        } return null; }

    private Account findAccountName(String name) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(name)) {
                return account; }
        } return null; }

    private Account findAccountPN(String personnummer) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(personnummer)) {
                return account; }
        } return null; }
}