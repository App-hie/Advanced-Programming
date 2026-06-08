public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("123456789", 1000.00);
        
        DepositTransaction deposit = new DepositTransaction(500.00);
        deposit.printTransactionDetails();
        deposit.apply(account);
        
        WithdrawalTransaction withdrawal = new WithdrawalTransaction(200.00);
        withdrawal.printTransactionDetails();
        
        try {
            withdrawal.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        
        withdrawal.reverse();
        
        WithdrawalTransaction partialWithdrawal = new WithdrawalTransaction(2000.00);
        partialWithdrawal.apply(account, true);
        
        BaseTransaction baseTx1 = (BaseTransaction) deposit;
        baseTx1.printTransactionDetails();
        try {
            baseTx1.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
        
        BaseTransaction baseTx2 = (BaseTransaction) withdrawal;
        baseTx2.printTransactionDetails();
        try {
            baseTx2.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
