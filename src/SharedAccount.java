public class SharedAccount {

    private double balance;
    private final String accountName;

    public SharedAccount(String accountName, double initialBalance) {
        this.accountName = accountName;
        this.balance = initialBalance;
        System.out.println("=============================================================");
        System.out.println("  ACCOUNT CREATED: " + accountName);
        System.out.printf("  Initial Balance: %.2f TL%n", initialBalance);
        System.out.println("=============================================================\n");
    }

    public synchronized void deposit(double amount) {
        String threadName = Thread.currentThread().getName();

        System.out.printf("[%s] >> Deposit initiated: %.2f TL%n",
                threadName, amount);

        balance += amount;

        System.out.printf("[%s] ++ DEPOSITED: %.2f TL | New Balance: %.2f TL%n",
                threadName, amount, balance);

        notifyAll();
    }

    public synchronized void withdraw(double amount) {
        String threadName = Thread.currentThread().getName();

        System.out.printf("[%s] << Withdrawal initiated: %.2f TL%n",
                threadName, amount);

        while (balance < amount) {
            System.out.printf("[%s] !! WAITING: Insufficient balance. "
                            + "Current: %.2f TL, Required: %.2f TL%n",
                    threadName, balance, amount);
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("[%s] !! Thread interrupted.%n", threadName);
                return;
            }
            System.out.printf("[%s] ~~ Woke up, re-checking balance...%n", threadName);
        }

        balance -= amount;

        System.out.printf("[%s] -- WITHDRAWN: %.2f TL | Remaining Balance: %.2f TL%n",
                threadName, amount, balance);

        notifyAll();
    }

    public synchronized double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("SharedAccount{account='%s', balance=%.2f TL}",
                accountName, balance);
    }
}
