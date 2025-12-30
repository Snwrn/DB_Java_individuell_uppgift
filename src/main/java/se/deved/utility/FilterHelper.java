package se.deved.utility;

import se.deved.models.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Scanner;

public class FilterHelper {

    public static double processTransaction(Transaction t, int filterTypeChoice, double total) {
        if (t.getIsDeposit() && filterTypeChoice == 1) {
            System.out.println("Deposit: " + t.getFormattedAmount() + " *** Date:" + t.getDate() + ";");
            total += t.getAmount();
        } else if (!t.getIsDeposit() && filterTypeChoice == 2) {
            System.out.println("Withdrawal: " + t.getFormattedAmount() + " *** Date:" + t.getDate() + ";");
            total += t.getAmount();
        }
        return total;
    }

    //prints total
    public static void printTotal(double total, int filterTypeChoice) {
        if (filterTypeChoice == 1) {
            System.out.println("Total deposits during period is " + se.deved.utility.DoubleFormatHelper.formatDouble(total));
        } else {
            System.out.println("Total withdrawals during period is " + se.deved.utility.DoubleFormatHelper.formatDouble(total));
        }
    }

    //validates that the year is 4 digits
    public static int getYearFromUserInput() {
        int filterYear = 0;
        Scanner scanner = ScannerHelper.getScanner();
        while (filterYear == 0) {
            System.out.print("Please write a year:");
            String userInput = scanner.nextLine().trim();
            try {
                if (userInput.matches("\\d{4}")) {
                    filterYear = Integer.parseInt(userInput);
                } else System.out.println("Invalid input. The year should be 4 digits (e.g., 2025).");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid year (e.g., 2025).");
            }
        }
        return filterYear;
    }

    public static int getMonthFromUserInput() {
        Scanner scanner = ScannerHelper.getScanner();
        int month = 0;
        while (month == 0) {
            System.out.print("Please write a month (1-12): ");
            String input = scanner.nextLine().trim();
            try {
                int m = Integer.parseInt(input);
                if (m >= 1 && m <= 12) {
                    month = m;
                } else {
                    System.out.println("Please enter a number between 1 and 12.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (e.g., 9 for September).");
            }
        }
        return month;
    }

    public static LocalDate getDayFromUserInput() {
        Scanner scanner = ScannerHelper.getScanner();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = null;
        while (date == null) {
            System.out.print("Please write a date (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                date = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use yyyy-MM-dd (example: 2025-10-04).");
            }
        }
        return date;
    }

    public static int getWeekNumberFromUserInput() {
        Scanner scanner = ScannerHelper.getScanner();
        int week = 0;
        while (week == 0) {
            System.out.print("Please enter week number (1-53): ");
            String input = scanner.nextLine().trim();
            try {
                int w = Integer.parseInt(input);
                if (w >= 1 && w <= 53) {
                    week = w;
                } else {
                    System.out.println("Please enter a valid week number (1-53).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 53.");
            }
        }
        return week;
    }

    public static int getWeekOfYear(LocalDate date) {
        return date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }
}

