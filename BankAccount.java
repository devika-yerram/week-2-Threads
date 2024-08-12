public class BankAccount {

    private int balance = 0;

    public synchronized void deposit(int amount) {
        System.out.println("Depositing " + amount);
        balance += amount;
        System.out.println("New balance after deposit: " + balance);
    }

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            System.out.println("Withdrawing " + amount);
            balance -= amount;
            System.out.println("New balance after withdrawal: " + balance);
        } else {
            System.out.println("Insufficient funds for withdrawal of " + amount);
        }
    }

    public synchronized int getBalance() {
        return balance;
    }

    static class Transaction extends Thread {
        private BankAccount account;
        private boolean isDeposit;
        private int amount;

        public Transaction(BankAccount account, boolean isDeposit, int amount) {
            this.account = account;
            this.isDeposit = isDeposit;
            this.amount = amount;
        }

        @Override
        public void run() {
            if (isDeposit) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }
        }
    }

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        Transaction t1 = new Transaction(account, true, 100);
        Transaction t2 = new Transaction(account, false, 50);
        Transaction t3 = new Transaction(account, true, 200);
        Transaction t4 = new Transaction(account, false, 150);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted.");
        }

        System.out.println("Final balance: " + account.getBalance());
    }
}