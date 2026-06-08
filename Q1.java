import java.util.Calendar;
import java.util.UUID;
 
// Class for Insufficient Funds
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
 
class BankAccount {
    private String accountNumber;
    private double balance;
 
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
 
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
 
    public void deposit(double amount) {
        balance += amount;
    }
 
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            // Throwing custom exception when funds are low
            throw new InsufficientFundsException("Insufficient funds! Available balance is $" + balance);
        }
        balance -= amount;
    }
}
 
//  Transaction Interface defining the required contract
interface TransactionInterface {
    double getAmount();
    Calendar getDate();
    String getTransactionID();
    void printTransactionDetails();
    void apply(BankAccount ba) throws InsufficientFundsException;
}
 
//  Base Class implementing the Interface
class BaseTransaction implements TransactionInterface {
    protected double amount;
    protected Calendar date;
    protected String transactionID;
 
    public BaseTransaction(double amount) {
        this.amount = amount;
        this.date = Calendar.getInstance(); 
        this.transactionID = UUID.randomUUID().toString(); // Generates a unique string ID
    }
 
    @Override
    public double getAmount() { 
        return amount; 
    }
 
    @Override
    public Calendar getDate() { 
        return date; 
    }
 
    @Override
    public String getTransactionID() { 
        return transactionID; 
    }
 
    @Override
    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Date: " + date.getTime());
        System.out.println("Amount: $" + amount);
    }
 
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        System.out.println("System Log: BaseTransaction invoked on Account " + ba.getAccountNumber() + ". No balance modified.");
    }
}
 
// Derived Class for Deposits
class DepositTransaction extends BaseTransaction {
    
    public DepositTransaction(double amount) {
        super(amount);
    }
 
    @Override
    public void printTransactionDetails() {
        System.out.println("\n--- Deposit Transaction ---");
        super.printTransactionDetails();
    }
 
    // Overriding apply() to perform a specific credit logic
    @Override
    public void apply(BankAccount ba) {
        ba.deposit(this.amount);
        System.out.println("Deposit of $" + this.amount + " applied. New Balance: $" + ba.getBalance());
    }
}
 
// Derived Class for Withdrawals
class WithdrawalTransaction extends BaseTransaction {
 
    public WithdrawalTransaction(double amount) {
        super(amount);
    }
 
    @Override
    public void printTransactionDetails() {
        System.out.println("\n--- Withdrawal Transaction ---");
        super.printTransactionDetails();
    }
 
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        ba.withdraw(this.amount);
        System.out.println("Withdrawal of $" + this.amount + " applied. New Balance: $" + ba.getBalance());
    }
}
 
public class Main {
    public static void main(String[] args) {
        // Initialize a test account
        BankAccount account = new BankAccount("ACC-987654321", 500.00);
        System.out.println("Initial Account Balance: $" + account.getBalance());
 
        TransactionInterface genericTx = new BaseTransaction(0.0);
        TransactionInterface depositTx = new DepositTransaction(250.00);
        TransactionInterface validWithdrawalTx = new WithdrawalTransaction(150.00);
        TransactionInterface invalidWithdrawalTx = new WithdrawalTransaction(1000.00);
 
        try {
            System.out.println("\n[Testing Base Transaction]");
            genericTx.printTransactionDetails();
            genericTx.apply(account);
 
            System.out.println("\n[Testing Deposit]");
            depositTx.printTransactionDetails();
            depositTx.apply(account);
 
            System.out.println("\n[Testing Valid Withdrawal]");
            validWithdrawalTx.printTransactionDetails();
            validWithdrawalTx.apply(account);
 
            System.out.println("\n[Testing Invalid Withdrawal]");
            invalidWithdrawalTx.printTransactionDetails();
            invalidWithdrawalTx.apply(account); // This line throws the exception
 
        } catch (InsufficientFundsException e) {
            System.err.println("TRANSACTION FAILED: " + e.getMessage());
        }
        
        System.out.println("\nFinal Account Balance: $" + account.getBalance());
    }
}