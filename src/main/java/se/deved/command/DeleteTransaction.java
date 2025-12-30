package se.deved.command;


import se.deved.models.Transaction;

import java.util.List;

import static se.deved.service.TransactionService.repository;

public class DeleteTransaction extends Command {
    public DeleteTransaction() {
        super("Delete a transaction", "Remove a transactions from the logs, restore balance.");
    }

    @Override
    public void execute() {

        List<Transaction> transactions;
        try {
            transactions = repository.loadTransactions();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (transactions.isEmpty()) {
            System.out.println("Transaction log is empty.");
            backToMenu();
            return;
        }

        System.out.println("Transaction log:");
        for (Transaction t : transactions) {
            System.out.println(
                    "ID: " + t.getId() +
                            " | " + t.transactionDate +
                            " | " + t.amount
            );
        }

        System.out.print("Enter transaction ID to delete (or 'menu' to return): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("menu")) {
            backToMenu();
            return;
        }

        try {
            int id = Integer.parseInt(input);

            double balance = repository.getCurrentBalance();
            Transaction toDelete = transactions.stream()
                    .filter(t -> t.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (toDelete == null) {
                System.out.println("Transaction not found.");
                return;
            }

            if (balance - toDelete.amount < 0) {
                System.out.println("Cannot delete — balance would become negative.");
                return;
            }

            try {
                repository.delete(id);
                System.out.println("Transaction deleted.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }

        backToMenu();
    }
}


