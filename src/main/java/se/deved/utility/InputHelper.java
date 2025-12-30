package se.deved.utility;

import java.util.Scanner;

//Validates input
public class InputHelper {
    public static double MAX_AMOUNT = 1000000000000000.00;
    public static double getValidAmount(Scanner scanner) {
    while (true) {
        String input = scanner.nextLine().trim();
        double amount = Double.parseDouble(input);

        if (amount < 0) {
            System.out.println("Amount must be positive.");
            continue;
        }

        if (amount > MAX_AMOUNT) {
            System.out.println("Amount too large. Try again.");
            continue;
        }
        if (validateNumericInput(input)==null) {
            continue;
        }

        return amount;
    }
}
    public static Integer validateNumericInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return null;
        }
    }
}