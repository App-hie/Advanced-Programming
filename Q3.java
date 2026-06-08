class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
 
class WithdrawalTransaction extends BaseTransaction {
    
    private BankAccount account;
    private boolean reversed;
    private double amountNotWithdrawn;
 
    public WithdrawalTransaction(double amount) {
        super(amount);
        this.reversed = false;
        this.amountNotWithdrawn = 0.0;
    }
 
    public void printTransactionDetails() {
        System.out.println("Withdrawal Transaction");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date: " + getDate().getTime());
        System.out.println("Amount Requested: $" + getAmount());
        System.out.println("Amount Not Withdrawn: $" + amountNotWithdrawn);
    }
 
    public void apply(BankAccount ba) throws InsufficientFundsException {
        if (this.amount > ba.getBalance()) {
            throw new InsufficientFundsException("Insufficient funds available.");
        }
        ba.withdraw(this.amount);
        this.account = ba;
        System.out.println("Withdrawal of $" + this.amount + " applied. New Balance: $" + ba.getBalance());
    }
 
    public void apply(BankAccount ba, boolean allowPartial) {
        try {
            if (ba.getBalance() <= 0) {
                throw new InsufficientFundsException("Balance is zero or negative. No funds to withdraw.");
            } 
            
            if (ba.getBalance() > 0 && ba.getBalance() < this.amount) {
                this.amountNotWithdrawn = this.amount - ba.getBalance();
                double availableFunds = ba.getBalance();
                ba.withdraw(availableFunds);
                this.account = ba;
                System.out.println("Partial withdrawal of $" + availableFunds + " applied. Shortfall: $" + this.amountNotWithdrawn);
            } else {
                ba.withdraw(this.amount);
                this.account = ba;
                System.out.println("Full withdrawal of $" + this.amount + " applied. New Balance: $" + ba.getBalance());
            }
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction Error: " + e.getMessage());
        } finally {
            System.out.println("Overloaded withdrawal attempt concluded. Final balance is $" + ba.getBalance());
        }
    }
 
    public boolean reverse() {
        if (this.account != null && !this.reversed) {
            this.account.deposit(this.amount - this.amountNotWithdrawn);
            this.reversed = true;
            System.out.println("Withdrawal reversed. Balance restored to: $" + this.account.getBalance());
            return true;
        }
        return false;
    }
}