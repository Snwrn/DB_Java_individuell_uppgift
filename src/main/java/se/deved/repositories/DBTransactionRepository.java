package se.deved.repositories;

import se.deved.models.Transaction;

import java.sql.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DBTransactionRepository implements ITransactionRepository {

    public Connection conn;

    //create table if not exists
    public DBTransactionRepository() {
        try {
            conn = se.deved.utility.DBConnectionHelper.getConnection();
            Statement createTablesStatement = conn.createStatement();
            createTablesStatement.execute(
                    "CREATE TABLE IF NOT EXISTS transactions " +
                            "(id SERIAL PRIMARY KEY, " +
                            "amount DOUBLE PRECISION, " +
                            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                            "is_deposit BOOLEAN)");
            createTablesStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //Get current balance
    @Override
    public double getCurrentBalance() {

        try (
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COALESCE(SUM(amount), 0) AS balance FROM transactions")
        ) {
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //delete a transaction
    @Override
    public void delete(int id) throws Exception {
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM transactions WHERE id = ?")) {
            statement.setObject(1, id);
            statement.executeUpdate();
        }
    }

    //add a transaction to the database, used in addTransaction
    @Override
    public void saveTransactions(double amount, boolean isDeposit) throws IOException {
        try {
            PreparedStatement insertTransactionStatement = conn.prepareStatement("INSERT INTO transactions(amount, is_deposit) VALUES(?,?)");
            insertTransactionStatement.setDouble(1, amount);
            insertTransactionStatement.setBoolean(2, isDeposit);
            insertTransactionStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get transactions from the database
    @Override
    public List<Transaction> loadTransactions() throws SQLException {

        List<Transaction> transactions = new ArrayList<>();
        Statement listTransactionsStatement = conn.createStatement();
        ResultSet resultSet = listTransactionsStatement.executeQuery("SELECT * FROM transactions");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            double amount = resultSet.getDouble("amount");
            LocalDate date =
                    resultSet.getTimestamp("created_at")
                            .toLocalDateTime()
                            .toLocalDate();
            boolean isDeposit = resultSet.getBoolean("is_deposit");

            Transaction transaction =
                    new Transaction(id, amount, date, isDeposit);

            transactions.add(transaction);
        }
        return transactions;
    }


    //Filter functions:
    @Override
    public List<Transaction> findByYear(int year) throws Exception {

        String sql = """
        SELECT *
        FROM transactions
        WHERE EXTRACT(YEAR FROM created_at) = ?
        ORDER BY created_at
        """;

        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, year);

            ResultSet set = statement.executeQuery();

            List<Transaction> transactions = new ArrayList<>();

            while (set.next()) {

                int id = set.getInt("id");
                double amount = set.getDouble("amount");

                LocalDate date =
                        set.getTimestamp("created_at")
                                .toLocalDateTime()
                                .toLocalDate();

                boolean isDeposit = set.getBoolean("is_deposit");

                transactions.add(
                        new Transaction(id, amount, date, isDeposit)
                );
            }

            return transactions;
        }
    }

    //Filter by month
    public List<Transaction> findByMonth(int year, int month) throws Exception {

        String sql = """
        SELECT *
        FROM transactions
        WHERE EXTRACT(YEAR FROM created_at) = ?
          AND EXTRACT(MONTH FROM created_at) = ?
        ORDER BY created_at
        """;

        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, year);
            statement.setInt(2, month);

            ResultSet set = statement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();

            while (set.next()) {

                int id = set.getInt("id");
                double amount = set.getDouble("amount");

                LocalDate date = set.getTimestamp("created_at")
                        .toLocalDateTime()
                        .toLocalDate();

                boolean isDeposit = set.getBoolean("is_deposit");

                transactions.add(
                        new Transaction(id, amount, date, isDeposit)
                );
            }

            return transactions;
        }
    }

    //Filter by day
    @Override
    public List<Transaction> findByDay(LocalDate day) throws Exception {

        String sql = """
        SELECT *
        FROM transactions
        WHERE created_at::date = ?
        ORDER BY created_at
        """;

        try (PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(day));

            ResultSet set = statement.executeQuery();

            List<Transaction> transactions = new ArrayList<>();

            while (set.next()) {

                int id = set.getInt("id");
                double amount = set.getDouble("amount");

                LocalDate date =
                        set.getTimestamp("created_at")
                                .toLocalDateTime()
                                .toLocalDate();

                boolean isDeposit = set.getBoolean("is_deposit");

                transactions.add(
                        new Transaction(id, amount, date, isDeposit)
                );
            }

            return transactions;
        }
    }

}
