package org.example.bankingsystem.ui;

import org.example.bankingsystem.service.TransactionService;
import java.util.Scanner;

public class TransactionPage {
    private final TransactionService service = new TransactionService();
    private final Scanner scanner;

    public TransactionPage(Scanner scanner) { this.scanner = scanner; }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Transactions ---");
            System.out.println("1. List All Transactions");
            System.out.println("2. Back");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(scanner.nextLine());
            switch (ch) {
                case 1 -> listAll();
                case 2 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void listAll() {
        service.findAll().forEach(System.out::println);
    }
}
