package org.example.bankingsystem.ui;

import org.example.bankingsystem.model.Customer;
import org.example.bankingsystem.service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerPage {
    private final CustomerService service = new CustomerService();
    private final Scanner scanner;

    public CustomerPage(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customers ---");
            System.out.println("1. Create Customer");
            System.out.println("2. List Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(scanner.nextLine());
            switch (ch) {
                case 1 -> create();
                case 2 -> list();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void create() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Phone: "); String phone = scanner.nextLine();
        Customer c = new Customer(name, email, phone);
        c = service.create(c);
        System.out.println("Created: " + c);
    }

    private void list() {
        List<Customer> list = service.findAll();
        list.forEach(System.out::println);
    }

    private void update() {
        System.out.print("Customer ID to update: "); Long id = Long.parseLong(scanner.nextLine());
        Customer c = service.findById(id);
        if (c == null) { System.out.println("Not found"); return; }
        System.out.print("New name (enter to keep) ["+c.getName()+"]: "); String name = scanner.nextLine();
        System.out.print("New email (enter to keep) ["+c.getEmail()+"]: "); String email = scanner.nextLine();
        System.out.print("New phone (enter to keep) ["+c.getPhone()+"]: "); String phone = scanner.nextLine();
        if (!name.isBlank()) c.setName(name);
        if (!email.isBlank()) c.setEmail(email);
        if (!phone.isBlank()) c.setPhone(phone);
        boolean ok = service.update(c);
        System.out.println(ok ? "Updated" : "Update failed");
    }

    private void delete() {
        System.out.print("Customer ID to delete: "); Long id = Long.parseLong(scanner.nextLine());
        boolean ok = service.delete(id);
        System.out.println(ok ? "Deleted" : "Delete failed");
    }
}
