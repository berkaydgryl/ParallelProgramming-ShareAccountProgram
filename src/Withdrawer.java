public class Withdrawer implements Runnable {

    private final SharedAccount account;
    private final double withdrawAmount;
    private final int transactionCount;

    public Withdrawer(SharedAccount account, double withdrawAmount, int transactionCount) {
        this.account = account;
        this.withdrawAmount = withdrawAmount;
        this.transactionCount = transactionCount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        for (int i = 1; i <= transactionCount; i++) {
            System.out.printf("%n[%s] ===== Transaction %d/%d =====%n",
                    threadName, i, transactionCount);

            account.withdraw(withdrawAmount);

            try {
                Thread.sleep((long) (Math.random() * 400 + 200));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("[%s] !! Withdrawer interrupted.%n", threadName);
                return;
            }
        }

        System.out.printf("%n[%s] ** Withdrawer completed all transactions. **%n", threadName);
    }
}
