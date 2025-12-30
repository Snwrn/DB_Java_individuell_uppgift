package se.deved.command;

import java.sql.SQLException;

import static se.deved.service.TransactionService.repository;
import static se.deved.service.TransactionService.transactions;

public class SeeTransactionHistory extends Command {
    public SeeTransactionHistory() {
        super("Transaction history", "See Transaction History");
    }

    @Override
    public void execute() {
        try {
        var transactions = repository.loadTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (var t : transactions) {
                System.out.println(
                        t.getId() + " | " +
                        t.transactionDate + " | " +
                                t.amount + " | " +
                                (t.isDeposit ? "Deposit" : "Withdrawal")
                );
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        backToMenu();
    }
}