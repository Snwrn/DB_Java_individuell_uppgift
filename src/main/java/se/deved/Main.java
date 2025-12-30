package se.deved;

import se.deved.command.*;
import se.deved.models.*;
import se.deved.repositories.DBTransactionRepository;

import java.sql.SQLException;

public class Main {
    static DBTransactionRepository repository = new DBTransactionRepository();

    public static void main(String[] args) {

        TerminalCommandService commandService = new TerminalCommandService();
        commandService.registerCommand(new AddTransaction());
        commandService.registerCommand(new DeleteTransaction());
        commandService.registerCommand(new SeeBalance());
        commandService.registerCommand(new SeeTransactionHistory());
        commandService.registerCommand(new FilterTransactions());

        //if (commandService instanceof TerminalCommandService service) {
        commandService.start();

        try {
            se.deved.utility.DBConnectionHelper.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return;
        }
    }
}