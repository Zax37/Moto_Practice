package pl.zaxola.moto.practice.day1;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private String header = "DATE       | AMOUNT  | BALANCE";
    private double balance = 0.0;
    private List<Transaction> logs = new ArrayList<>();

    public void printStatements(PrintStream printer) {
        StringBuilder stringBuilder = new StringBuilder(header);
        if (logs.isEmpty()) {
            stringBuilder.append(getActionLog(new Transaction(getCurrentDate(), 0, 0)));
        } else {
            logs.forEach(transaction -> stringBuilder.append(getActionLog(transaction)));
        }
        printer.println(stringBuilder.toString());
    }

    private String formatBalance(double balance) {
        return Double.toString(balance);
    }

    private String getActionLog(Transaction transaction) {
        final String formattedAmount = formatBalance(transaction.amount);
        final String formattedBalance = formatBalance(transaction.balanceAfter);

        StringBuilder logLine = new StringBuilder("\n");
        logLine.append(transaction.date);
        logLine.append(" | ");
        logLine.append(formattedAmount);
        logLine.append("| ");
        logLine.append(formattedBalance);

        return logLine.toString();
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public void withdraw(double v) {
        balance -= v;
        logs.add(new Transaction(getCurrentDate(), -v, balance));
    }

    public void deposit(double v) {
        balance += v;
        logs.add(new Transaction(getCurrentDate(), v, balance));
    }
}
