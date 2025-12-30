package se.deved.command;

import se.deved.models.FilterChoice;
import se.deved.models.Transaction;
import se.deved.service.TransactionService;
import se.deved.utility.FilterHelper;
import se.deved.utility.InputHelper;

import static se.deved.service.TransactionService.repository;

import java.time.LocalDate;
import java.util.List;

public class FilterTransactions extends Command {
    public FilterTransactions() {
        super("Filter transactions", "Filter transactions and see balance by year, month week or day.");
    }

    @Override
    public void execute() {

        //Choose type (deposit or withdrawal)
        System.out.println("Choose transaction type:");
        System.out.println("1. Deposits");
        System.out.println("2. Withdrawals");
        int depositOrWithdrawalChoice = 0;
        while (depositOrWithdrawalChoice != 1 && depositOrWithdrawalChoice != 2) {
            String userInput = scanner.nextLine();
            Integer validatedInput = InputHelper.validateNumericInput(userInput);

            if (validatedInput != null && (validatedInput == 1 || validatedInput == 2)) {
                depositOrWithdrawalChoice = validatedInput;
            } else if (validatedInput != null) {
                System.out.println("Invalid choice. Please enter 1 (Deposits) or 2 (Withdrawals).");
            }
        }

        // Show available filters
        for (FilterChoice choice : FilterChoice.values()) {
            System.out.println(choice);
        }

        // User chooses filter
        int usersFilterChoice = 0;
        while (usersFilterChoice == 0) {
            System.out.print("Enter the number of the filter you want to use: ");
            String userInput2 = scanner.nextLine();
            Integer validatedInput2 = InputHelper.validateNumericInput(userInput2);

            if ((validatedInput2 != null && validatedInput2 < 1) || (validatedInput2 != null && validatedInput2 > FilterChoice.values().length)) {
                System.out.println("Invalid choice. Please enter a valid number from the list.");
            } else if (validatedInput2 != null) {
                usersFilterChoice = validatedInput2;
            }
        }

        //to count total
        double total = 0;

        // By year
        if (usersFilterChoice == FilterChoice.BY_YEAR.getID()) {
            int year = FilterHelper.getYearFromUserInput();
            List<Transaction> filteredTransactions = null;
            try {
                filteredTransactions = repository.findByYear(year);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Transaction t : filteredTransactions) {
                total = FilterHelper.processTransaction(t, depositOrWithdrawalChoice, total);
            }
        }

        // By month
        else if (usersFilterChoice == FilterChoice.BY_MONTH.getID()) {
            int year = FilterHelper.getYearFromUserInput();
            int month = FilterHelper.getMonthFromUserInput();
            List<Transaction> filteredTransactions = null;
            try {
                filteredTransactions = repository.findByMonth(year, month);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Transaction t : filteredTransactions) {
                total = FilterHelper.processTransaction(t, depositOrWithdrawalChoice, total);
            }
        }

        // By day
        else if (usersFilterChoice == FilterChoice.BY_DAY.getID()) {
            LocalDate day = FilterHelper.getDayFromUserInput();
            List<Transaction> filteredTransactions = null;
            try {
                filteredTransactions = repository.findByDay(day);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Transaction t : filteredTransactions) {
                total = FilterHelper.processTransaction(t, depositOrWithdrawalChoice, total);
            }
        }

        // By week
        else if (usersFilterChoice == FilterChoice.BY_WEEK.getID()) {
            int year = FilterHelper.getYearFromUserInput();
            int week = FilterHelper.getWeekNumberFromUserInput();
            for (Transaction t : TransactionService.transactions) {
                int transactionWeek = FilterHelper.getWeekOfYear(t.getDate());
                if (t.getDate().getYear() == year && transactionWeek == week) {
                    total = FilterHelper.processTransaction(t, depositOrWithdrawalChoice, total);
                }
            }
        }

        // Print total
        FilterHelper.printTotal(total, depositOrWithdrawalChoice);
        backToMenu();
    }
}


