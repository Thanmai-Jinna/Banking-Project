package org.example.bankingsystem.main;

import org.example.bankingsystem.ui.AccountPage;
import org.example.bankingsystem.ui.CustomerPage;
import org.example.bankingsystem.ui.TransactionPage;
import org.example.bankingsystem.util.DBConnection;

import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("   WELCOME TO BANKING SYSTEM APP  ");
        System.out.println("==================================");

        DBConnection.getConnection();

        Scanner scanner = new Scanner(System.in);
        CustomerPage customerPage = new CustomerPage(scanner);
        AccountPage accountPage = new AccountPage(scanner);
        TransactionPage transactionPage = new TransactionPage(scanner);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Customers");
            System.out.println("2. Manage Accounts");
            System.out.println("3. Transactions");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();
            int choice = 0;
            try { choice = Integer.parseInt(input); } catch (NumberFormatException ignored) {}
            switch (choice) {
                case 1 -> customerPage.show();
                case 2 -> accountPage.show();
                case 3 -> transactionPage.show();
                case 4 -> {
                    running = false;
                    System.out.println("Exiting system...");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        DBConnection.closeConnection();
        scanner.close();
    }
}
