package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BankingApp {

    private ArrayList<Account> accounts;
    private HashSet<Customer> customers;
    private Scanner scan;

    public void run() throws InterruptedException {

        scan = new Scanner(System.in);
        boolean loop = true;
        accounts = new ArrayList<>();
        customers = new HashSet<>();

        System.out.println("\n~~~~~ WELCOME TO THE BANKING PROGRAM™ ~~~~~");
        do {
            System.out.println("\nPLEASE SELECT FROM THE FOLLOWING CHOICES:");
            System.out.println("   1. Open a new account");
            System.out.println("   2. See info for existing account");
            System.out.println("   3. Deposit funds");
            System.out.println("   4. Withdraw funds");
            System.out.println("   5. Transfer funds");
            System.out.println("   6. Add customer info from datafile (.csv)");
            System.out.println("   7. Save customer info to datafile (.csv)");
            System.out.println("   0. Exit program");

            String menuChoice = scan.nextLine();
            Account account = null;

            switch (menuChoice) {

                //OPEN ACCOUNT
                case "1":
                    Customer selectedCustomer = null;
                    if (customers.size() == 0) {
                        selectedCustomer = showCreateNewCustomerMenu();
                    } else
                        while (selectedCustomer == null) {
                            System.out.println("Select an existing customer or add new:");
                            System.out.println("1. Select existing customer");
                            System.out.println("2. Add new customer");

                            String subMenuChoice = scan.nextLine();
                            switch (subMenuChoice) {
                                case "1":
                                    selectedCustomer = showSelectCustomerMenu(scan);
                                    break;
                                case "2":
                                    selectedCustomer = showCreateNewCustomerMenu();
                                    break;
                            }
                            if (selectedCustomer == null)
                                System.out.println("Could not find customer. Please select a customer.");
                        }

                    System.out.println("Please enter your initial deposit:");
                    double initialDeposit = scan.nextDouble();
                    scan.nextLine();

                    while (account == null) {
                        System.out.println("Please enter if you would like to open a "
                                + Savings.ACCOUNT_TYPE.toLowerCase() + " account or a "
                                + Checking.ACCOUNT_TYPE.toLowerCase() + " account: ");
                        String accountType = scan.nextLine();

                        if (accountType.equalsIgnoreCase(Savings.ACCOUNT_TYPE)) {
                            account = new Savings(selectedCustomer, initialDeposit);
                        } else if (accountType.equalsIgnoreCase(Checking.ACCOUNT_TYPE)) {
                            account = new Checking(selectedCustomer, initialDeposit);
                        }
                    }

                    System.out.println("\nA " +
                            account.getType().toLowerCase()
                            + " account has successfully been created for " + selectedCustomer.getName() + ", "
                            + selectedCustomer.getPersonnummer() + " and with the initial deposit of $"
                            + initialDeposit + "\n");
                    customers.add(selectedCustomer);
                    accounts.add(account);
                    account.showInfo();
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
                            String name = scan.nextLine().trim();

                            Account[] accountsArray = findAccountsByName(name);
                            if (accountsArray.length >= 1) {
                                System.out.println(accountsArray.length + " accounts found for " + name);
                                if (accountsArray.length == 1)
                                    account = accountsArray[0];
                                else
                                    account = selectAccountFromArray(scan, accountsArray);
                            } else
                                System.out.println("No accounts found for " + name);
                            break;

                        case "B":
                            System.out.println("\nEnter the personnummer of the account holder: ");
                            String personnummer = scan.nextLine().trim();

                            accountsArray = findAccountsByPN(personnummer);
                            if (accountsArray.length >= 1) {
                                System.out.println(accountsArray.length + " accounts found for " + personnummer);
                                if (accountsArray.length == 1)
                                    account = accountsArray[0];
                                else
                                    account = selectAccountFromArray(scan, accountsArray);
                            } else
                                System.out.println("No accounts found for " + personnummer);
                            break;

                        case "C":
                            System.out.println("\nEnter account number (11 numbers with dashes): ");
                            String accountNumber = scan.nextLine();

                            account = findAccountNumber(accountNumber);

                            break;
                    }
                    if (account != null) {
                        account.showInfo();
                    } else {
                        System.out.println("ERROR - Account not found");
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
                    if (account != null) {
                        account.deposit(depositFunds);
                        account.showInfo();
                    } else {
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
                        if (withdrawFunds < account.getBalance()) {
                            account.withdraw(withdrawFunds);
                            System.out.println("Account number " + account.getAccountNumber() + " now has the balance $" + account.getBalance());
                        } else {
                            System.out.println("\nChecking account...");
                            Thread.sleep(1200);
                            System.out.println("\nYou do not have enough funds in your account. Please try again!");
                        }
                    } else {
                        System.out.println("ERROR - Account not found. Please try again!");
                    }
                    break;


                //TRANSFER
                case "5":
                    System.out.println("\nPlease type in the account number that you would like to transfer funds from: ");
                    String fromAccount = scan.nextLine();

                    Account sendAccount = findAccountNumber(fromAccount);
                    if (sendAccount == null) {
                        System.out.println("ERROR - Account not found");
                        break;
                    }

                    System.out.println("\nHow much would you like to transfer?");
                    double transferAmount = scan.nextInt();
                    scan.nextLine();

                    System.out.println("\nPlease type in the account number that you would like to transfer funds to: ");
                    String toAccount = scan.nextLine();

                    Account receiveAccount = findAccountNumber(toAccount);
                    if (receiveAccount == null) {
                        System.out.println("ERROR - Account not found");
                    } else {
                        sendAccount.withdraw(transferAmount);
                        receiveAccount.deposit(transferAmount);

                        System.out.println("\nCompleting transfer, please wait...");
                        Thread.sleep(2000);
                        System.out.println("\nThe transfer of $" + transferAmount + " from account number " + fromAccount + " to account number " + toAccount + " has now been completed!\n");

                        System.out.println("\n*************************************");
                        sendAccount.showInfo();
                        System.out.println("*************************************");
                        receiveAccount.showInfo();
                        System.out.println("*************************************\n");
                    }
                    break;

                //READ CSV
                case "6":
                    System.out.println("\nPlease enter the full directory path to the datafile you would like to upload (only .csv)");
                    String file = scan.nextLine();                         //Min exempelfil: "/Users/Nima/Desktop/NewBankAccounts.csv"

                    accounts = CsvFileHandler.readFile(file);
                    if (accounts != null)
                        for (Account acc : accounts) {
                            customers.add(acc.getOwner());
                            acc.showInfo();
                            System.out.println("\n**************\n");
                        }
                    break;
                // SAVE CSV
                case "7":
                    System.out.println("\nPlease enter the full directory path to the save location");
                    file = scan.nextLine(); //Min exempelfil: "/Users/Nima/Desktop/NewBankAccounts.csv"

                    try {
                        CsvFileHandler.saveFile(accounts, file);
                        System.out.println("\nCustomer info has successfully been saved.");
                    } catch (IOException e) {
                        System.out.println("\nCould not write to file!");
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

    /**
     * Shows a menu to select a specific Customer
     *
     * @param scan Scanner-object to use for input
     * @return Customer-object if exists, otherwise null
     */
    private Customer showSelectCustomerMenu(Scanner scan) {
        String menuChoice;
        System.out.println("Select customer by name or personnummer");
        System.out.println("1. Search customer by name");
        System.out.println("2. Search customer by personnummer");
        menuChoice = scan.nextLine();

        Customer[] customerResults = null;
        switch (menuChoice) {
            case "1":
                System.out.println("Enter customer name: ");
                String input = scan.nextLine();
                customerResults = getCustomersByName(input);
                break;
            case "2":
                System.out.println("Enter customer personnummer: ");
                input = scan.nextLine();
                customerResults = getCustomersByPN(input);
                break;
        }
        if (customerResults == null || customerResults.length == 0)
            return null;
        else if (customerResults.length == 1)
            return customerResults[0];
        else {
            // Found multiple matching customers
            System.out.println("Select a customer:");
            for (int i = 0; i < customerResults.length; i++) {
                Customer currentCustomer = customerResults[i];
                System.out.printf("%d. %s%n", i + 1, currentCustomer.getName() + " (" + currentCustomer.getPersonnummer() + ")");
            }
            return customerResults[Integer.parseInt(scan.nextLine().trim()) - 1];
        }
    }

    /**
     * Get an array of customers with the matching personnumber
     *
     * @param input Personnummer-string
     * @return Customer-array
     */
    private Customer[] getCustomersByPN(String input) {
        String formatedInput = Customer.Personnummer.convertToPersonnummerFormat(input);
        return customers.stream().filter(customer -> customer.getPersonnummer().toString().equals(formatedInput)).toArray(Customer[]::new);
    }

    /**
     * Get an array of customers with the matching name
     *
     * @param input Name-string
     * @return Customer-array
     */
    private Customer[] getCustomersByName(String input) {
        return customers.stream().filter(customer -> customer.getName().toLowerCase().equals(input.toLowerCase())).toArray(Customer[]::new);
    }

    /**
     * Menu to create a new customer
     *
     * @return The newly created Customer-object
     */
    private Customer showCreateNewCustomerMenu() {
        System.out.println("Please enter the full name of the account holder: ");
        String name = scan.nextLine();

        System.out.println("Please enter the personnummer (10 digits) of the account holder: ");
        String personnummer = Customer.Personnummer.convertToPersonnummerFormat(scan.nextLine());
        return new Customer(name, personnummer);
    }

    /**
     * Shows a menu to select an account from an array of accounts.
     *
     * @param scan          Scanner-object for input
     * @param accountsArray Account-array
     * @return Selected account or null
     */
    private Account selectAccountFromArray(Scanner scan, Account[] accountsArray) {
        if (accountsArray.length >= 1) {
            System.out.println("Select account: ");
            for (int i = 0; i < accountsArray.length; i++) {
                System.out.printf("%d. %s%n", i + 1, accountsArray[i].getAccountNumber());
            }
            String input = scan.nextLine();
            try {
                return accountsArray[Integer.parseInt(input) - 1];
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    private Account findAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private Account[] findAccountsByName(String name) {
        return accounts.stream().
                filter(account -> account.getOwner().getName().equals(name)).
                toArray(Account[]::new);
    }

    private Account[] findAccountsByPN(String personnummer) {
        return accounts.stream().
                filter(account -> account.getOwner().getPersonnummer().toString().equals(Customer.Personnummer.convertToPersonnummerFormat(personnummer))).
                toArray(Account[]::new);
    }

    public static class CsvFileHandler {
        /**
         * Saves application data to file as CSV
         *
         * @param accountArrayList Array of accounts to be written to file.
         */
        public static void saveFile(ArrayList<Account> accountArrayList, String filePath) throws IOException {

            ArrayList<String> dataArrayList = (ArrayList<String>) accountArrayList.
                    stream().map(Account::toString).collect(Collectors.toList());

            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8, false)), true);
            for (String dataRow : dataArrayList) {
                writer.println(dataRow);
            }
            writer.flush();
            writer.close();
        }

        public static ArrayList<Account> readFile(String file) {
            ArrayList<String> fileRows = new ArrayList<>();
            String currentRow;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
                while ((currentRow = br.readLine()) != null) {
                    fileRows.add(currentRow);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Could not find file!");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                System.out.println("Could not read file!");
                e.printStackTrace();
                return null;
            }

            ArrayList<Account> accountsToReturn = new ArrayList<>();
            for (String row : fileRows) {
                String[] dataArray = row.split(",");
                String name = dataArray[0];
                String personnummer = dataArray[1];
                Customer loadedCustomer;
                loadedCustomer = new Customer(name, personnummer);

                String accountType = dataArray[2];
                double initialDeposit = Double.parseDouble(dataArray[3]);
                if (accountType.equals(Savings.ACCOUNT_TYPE)) {
                    accountsToReturn.add(new Savings(loadedCustomer, initialDeposit));
                } else if (accountType.equals(Checking.ACCOUNT_TYPE)) {
                    accountsToReturn.add(new Checking(loadedCustomer, initialDeposit));
                }
            }
            return accountsToReturn;
        }
    }
}