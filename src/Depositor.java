public class Depositor implements Runnable {

    private final SharedAccount account;
    private final double depositAmount;
    private final int transactionCount;

    public Depositor(SharedAccount account, double depositAmount, int transactionCount) {
        this.account = account;
        this.depositAmount = depositAmount;
        this.transactionCount = transactionCount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        for (int i = 1; i <= transactionCount; i++) {
            System.out.printf("%n[%s] ===== Transaction %d/%d =====%n",
                    threadName, i, transactionCount);

            account.deposit(depositAmount);

            try {
                Thread.sleep((long) (Math.random() * 400 + 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.printf("[%s] !! Depositor interrupted.%n", threadName);
                return;
            }
        }

        System.out.printf("%n[%s] ** Depositor completed all transactions. **%n", threadName);
    }
}
