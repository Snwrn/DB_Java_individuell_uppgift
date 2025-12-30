package se.deved.command;

import se.deved.utility.ScannerHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalCommandService implements ICommandService {

    private final List<Command> commands = new ArrayList<>();

//Terminal that displays menu and gets user input. also exits the program
    public void start() {
        System.out.println("=== APPLICATION ===");
        Scanner scanner = ScannerHelper.getScanner();
        while (true) {
            System.out.println("Choose a command:");

            for (Command command : commands) {
                System.out.println(command);
            }
            System.out.println((commands.size() + 1) + ". Exit");

            int choice = -1;

            while (choice < 1 || choice > (commands.size() + 1)) {
                System.out.print("Enter your choice: ");
                String commandInput = scanner.nextLine();

                Integer validatedInput = se.deved.utility.InputHelper.validateNumericInput(commandInput);

                if (validatedInput == null) {
                    continue;
                }
                choice = validatedInput;

                if (choice < 1 || choice > (commands.size() + 1)) {
                    System.out.println("Invalid number. Please choose between 1 and " + (commands.size() + 1) + ".");
                }
            }

            try {
                executeCommand(choice);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void exit() {
            System.out.println("Exiting program without saving...");
            System.exit(0);
    }

    @Override
    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    @Override
    public void executeCommand(int choice) throws IOException {
        for (Command command : commands) {
            if (command.getIdNumber() == choice) {
                command.execute();
                return;
            }
        }
        if (choice == (commands.size() + 1)) {
            exit();
        }
    }
}
