package se.deved.repositories;

import se.deved.models.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ITransactionRepository {

    //add new transaction
    void saveTransactions(double amount, boolean isDeposit) throws IOException;

    //load the list of all transactions
    List<Transaction> loadTransactions() throws Exception;

    //Get balance of all transations
    double getCurrentBalance();

    //remove transaction from memory
    void delete(int todoId) throws Exception;

    //Filter functions
    List<Transaction> findByYear(int year) throws Exception;

    List<Transaction> findByMonth(int year, int month) throws Exception;

    List<Transaction> findByDay(LocalDate day) throws Exception;
}
