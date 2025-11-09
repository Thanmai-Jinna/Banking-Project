package org.example.bankingsystem.ui;

import org.example.bankingsystem.model.Account;
import org.example.bankingsystem.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class AccountPage {
    private final AccountService service = new AccountService();
    private final Scanner scanner;

    public AccountPage(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Accounts ---");
            System.out.println("1. Create Account");
            System.out.println("2. List Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. View Transactions");
            System.out.println("6. Back");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(scanner.nextLine());
            switch (ch) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> viewTransactions();
                case 6 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void create() {
        System.out.print("Customer ID: "); Long cid = Long.parseLong(scanner.nextLine());
        System.out.print("Account Number: "); String accNo = scanner.nextLine();
        System.out.print("Initial Balance: "); BigDecimal bal = new BigDecimal(scanner.nextLine());
        System.out.print("Type: "); String type = scanner.nextLine();
        Account a = new Account(cid, accNo, bal, type);
        a = service.create(a);
        System.out.println("Created: " + a);
    }

    private void list() {
        List<Account> list = service.findAll();
        list.forEach(System.out::println);
    }

    private void deposit() {
        System.out.print("Account ID: "); Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: "); BigDecimal amt = new BigDecimal(scanner.nextLine());
        System.out.print("Description: "); String desc = scanner.nextLine();
        boolean ok = service.deposit(id, amt, desc);
        System.out.println(ok ? "Deposit successful" : "Deposit failed");
    }

    private void withdraw() {
        System.out.print("Account ID: "); Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: "); BigDecimal amt = new BigDecimal(scanner.nextLine());
        System.out.print("Description: "); String desc = scanner.nextLine();
        boolean ok = service.withdraw(id, amt, desc);
        System.out.println(ok ? "Withdraw successful" : "Withdraw failed (insufficient funds?)");
    }

    private void viewTransactions() {
        System.out.print("Account ID: "); Long id = Long.parseLong(scanner.nextLine());
        service.getTransactions(id).forEach(System.out::println);
    }
}
