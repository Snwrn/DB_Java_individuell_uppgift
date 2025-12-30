package se.deved.command;

import se.deved.utility.ScannerHelper;

import java.io.IOException;
import java.util.Scanner;

import static se.deved.service.TransactionService.repository;

//for menu items, aka commands
public abstract class Command {
    private static int nextId = 1;
    protected final String name;
    protected final String description;
    private final int idNumber;
    Scanner scanner = ScannerHelper.getScanner();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.idNumber = nextId++;
    }

    public abstract void execute() throws IOException;

    //goes back to menu with a little delay animation
    public void backToMenu() {
        System.out.print("Going back to menu");
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(600); // wait 0.6 second between dots
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // good practice according to googled materials
        }
        System.out.println();
        System.out.println();// move to new line after dots
    }

    //prints anoverview of all transactions
    protected void printTransactionSummary() {
        try {
            var transactions = repository.loadTransactions();

            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                for (var t : transactions) {
                    System.out.println(
                            t.amount + " | " +
                                    t.transactionDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public String toString() {
        return idNumber + ". " + name + " - " + description;
    }

}
