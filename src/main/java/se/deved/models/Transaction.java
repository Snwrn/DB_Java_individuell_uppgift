package se.deved.models;

import java.time.LocalDate;

public class Transaction {
    int id;
    public double amount;
    public LocalDate transactionDate;
    public Boolean isDeposit;

    public Transaction(int id, double amount, LocalDate transactionDate, Boolean isDeposit) {
        this.id = id;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.isDeposit = isDeposit;
    }

    public double getAmount() {
        return amount;
    }

    public String getFormattedAmount() {
        return se.deved.utility.DoubleFormatHelper.formatDouble(amount);
    }

    public LocalDate getDate() {
        return transactionDate;
    }

    public Boolean getIsDeposit() {
        return isDeposit;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return transactionDate +
                " | " +
                amount;
    }

}
