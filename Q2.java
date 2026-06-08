class DepositTransaction extends BaseTransaction {
    
    public DepositTransaction(double amount) {
        super(amount);
    }
 
    @Override
    public void printTransactionDetails() {
        System.out.println("Deposit Transaction");
        super.printTransactionDetails();
    }
 
    @Override
    public void apply(BankAccount ba) {
        ba.deposit(this.amount);
        System.out.println("Deposit of $" + this.amount + " applied. New Balance: $" + ba.getBalance());
    }
}
 
class WithdrawalTransaction extends BaseTransaction {
    
    private BankAccount account;
    private boolean reversed;
 
    public WithdrawalTransaction(double amount) {
        super(amount);
        this.reversed = false;
    }
 
    @Override
    public void printTransactionDetails() {
        System.out.println("Withdrawal Transaction");
        super.printTransactionDetails();
    }
 
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        ba.withdraw(this.amount);
        this.account = ba;
        System.out.println("Withdrawal of $" + this.amount + " applied. New Balance: $" + ba.getBalance());
    }
 
    public boolean reverse() {
        if (this.account != null && !this.reversed) {
            this.account.deposit(this.amount);
            this.reversed = true;
            System.out.println("Withdrawal reversed. Balance restored to: $" + this.account.getBalance());
            return true;
        }
        return false;
    }
}