package pl.zaxola.moto.practice.day1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {
    @Mock
    PrintStream printer;

    @Test
    public void printOnlyHeaderWhenNoTransactionsDone() {
        Account account = new Account();
        account.printStatements(printer);
        verify(printer).println(expectedStatementWhenNoTransactionsDone());
    }

    @Test
    public void printAccountBalanceAfterOneTransaction() {
        Account account = new Account();
        account.withdraw(200.34);
        account.printStatements(printer);
        verify(printer).println(expectedStatementWhenOneTransactionDone());
    }

    private String expectedStatementWhenNoTransactionsDone() {
        return "DATE       | AMOUNT  | BALANCE\n"+currentDate+" | 0.0| 0.0";
    }

    private String expectedStatementWhenOneTransactionDone() {
        return "DATE       | AMOUNT  | BALANCE\n"+currentDate+" | -200.34| -200.34";
    }

    private String expectedStatementWhenTwoTransactionsDone() {
        return "DATE       | AMOUNT  | BALANCE\n"+currentDate+" | 100.34| 100.34\n"+currentDate+" | -200.34| -100.0";
    }

    private String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Test
    public void printAccountBalanceAfterTwoTransactions() {
        Account account = new Account();
        account.deposit(100.34);
        account.withdraw(200.34);
        account.printStatements(printer);
        verify(printer).println(expectedStatementWhenTwoTransactionsDone());
    }
}
