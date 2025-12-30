package se.deved.command;

import static se.deved.service.TransactionService.repository;

public class SeeTransactionHistory extends Command {
    public SeeTransactionHistory() {
        super("Transaction history", "See Transaction History");
    }

    @Override
    public void execute() {
        try {
        //Get list from the database
            var transactions = repository.loadTransactions();

        //if db is empty, inform the user
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            //else, show the ids, dates, amounts and type of all tranactions
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