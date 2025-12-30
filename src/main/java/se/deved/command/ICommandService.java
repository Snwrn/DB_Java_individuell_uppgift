package se.deved.command;

import java.io.IOException;

//interface for all the menu items, aka commands
public interface ICommandService {

    void registerCommand(Command command);

    void executeCommand(int choice) throws IOException;

}