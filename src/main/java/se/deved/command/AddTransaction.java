package se.deved.command;

import java.io.IOException;
import java.util.Scanner;

import static se.deved.service.TransactionService.repository;

public class AddTransaction extends Command {

    public AddTransaction() {
        super("Add a new transaction", "Add a new deposit or withdrawal.");
    }

    @Override
    public void execute() throws IOException {
        System.out.println("1. Deposit (add money to the account)");
        System.out.println("2. Withdrawal (remove money from the account)");
        int userChoice = getTransactionType(scanner);
        if (userChoice == 1) {
            handleDeposit(scanner);
        } else if (userChoice == 2) {
            handleWithdrawal(scanner);
        }
        printTransactionSummary();
        backToMenu();
    }

    private int getTransactionType(Scanner scanner) {
        while (true) {
            System.out.print("Choose transaction type (1 or 2): ");
            String input = scanner.nextLine().trim();
            if (input.equals("1") || input.equals("2")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid choice. Please type 1 or 2.");
        }
    }

    private void handleDeposit(Scanner scanner) throws IOException {

        System.out.print("Write how much is added to your account:");
        double amount = se.deved.utility.InputHelper.getValidAmount(scanner);

        double newBalance = repository.getCurrentBalance() + amount;
        if (newBalance > se.deved.utility.InputHelper.MAX_AMOUNT) {
            System.out.println("You have reached the maximum balance limit.");
            return;
        }
        repository.saveTransactions(amount, true);
    }

    private void handleWithdrawal(Scanner scanner) throws IOException {
        System.out.print("Write how much is withdrawn from your account:");
        double amount = se.deved.utility.InputHelper.getValidAmount(scanner);

        if (amount > repository.getCurrentBalance()) {
            System.out.println("Not enough balance. Transaction canceled.");
            return;
        }
        repository.saveTransactions(-amount, false);
    }
}
