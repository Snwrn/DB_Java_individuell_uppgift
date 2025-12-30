package se.deved.repositories;

import se.deved.models.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ITransactionRepository {

    void delete(int todoId) throws Exception;

    void saveTransactions(double amount, boolean isDeposit) throws IOException;

    List<Transaction> loadTransactions() throws Exception;

    double getCurrentBalance();

    List<Transaction> findByYear(int year) throws Exception;

    List<Transaction> findByMonth(int year, int month) throws Exception;

    List<Transaction> findByDay(LocalDate day) throws Exception;
}
