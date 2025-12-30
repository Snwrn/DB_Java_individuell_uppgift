package se.deved.command;

import static se.deved.service.TransactionService.repository;

public class SeeBalance extends Command {
    public SeeBalance() {
        super("See Balance", "See the total balance.");
    }

    @Override
    public void execute() {
        //get current blance from database, format the double in a readable way, display it
        String currentBalance = se.deved.utility.DoubleFormatHelper.formatDouble(repository.getCurrentBalance());
        System.out.println("Your current balance: " + currentBalance);
        backToMenu();
    }
}
