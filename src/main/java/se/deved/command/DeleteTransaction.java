package se.deved.command;


import se.deved.models.Transaction;

import java.util.List;

import static se.deved.service.TransactionService.repository;

public class DeleteTransaction extends Command {
    public DeleteTransaction() {
        super("Delete a transaction", "Remove a transactions from the database.");
    }

    @Override
    public void execute() {
        //Get list of all transactions form database
        List<Transaction> transactions;
        try {
            transactions = repository.loadTransactions();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //if the list is empty,inform user
        if (transactions.isEmpty()) {
            System.out.println("Transaction log is empty.");
            backToMenu();
            return;
        }

        //Print the list so the user can see ids
        System.out.println("Transaction log:");
        for (Transaction t : transactions) {
            System.out.println(
                    "ID: " + t.getId() +
                            " | " + t.transactionDate +
                            " | " + t.amount
            );
        }

        //Ask the user to choose which transaction to delete
        System.out.print("Enter transaction ID to delete (or 'menu' to return): ");
        String input = scanner.nextLine().trim();

        //Give possibility to return to menu
        if (input.equalsIgnoreCase("menu")) {
            backToMenu();
            return;
        }

        try {
            int id = Integer.parseInt(input);

            //prepare transaction fo deletion
            double balance = repository.getCurrentBalance();
            Transaction toDelete = transactions.stream()
                    .filter(t -> t.getId() == id)
                    .findFirst()
                    .orElse(null);


            //If not found
            if (toDelete == null) {
                System.out.println("Transaction not found.");
                return;
            }

            //Check if the balance would become negative
            if (balance - toDelete.amount < 0) {
                System.out.println("Cannot delete — balance would become negative.");
                return;
            }

            //Try to delete
            try {
                repository.delete(id);
                System.out.println("Transaction deleted.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //If input is invalid
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }

        //return to menu
        backToMenu();
    }
}


