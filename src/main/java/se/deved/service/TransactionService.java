package se.deved.service;

import se.deved.models.Transaction;
import se.deved.repositories.DBTransactionRepository;
import se.deved.repositories.ITransactionRepository;

import java.util.List;


public class TransactionService {
    //Service has a list that is filled from the database
    public static final ITransactionRepository repository = new DBTransactionRepository();
    public static List<Transaction> transactions;

    static {
        try {
            transactions = repository.loadTransactions();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
