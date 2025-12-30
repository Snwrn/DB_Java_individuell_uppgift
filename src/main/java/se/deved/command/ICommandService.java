package se.deved.command;

import java.io.IOException;

public interface ICommandService {

    void registerCommand(Command command);

    void executeCommand(int choice) throws IOException;

}