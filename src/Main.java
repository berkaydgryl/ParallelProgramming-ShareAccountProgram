public class Main {

    public static void main(String[] args) {

        System.out.println("*************************************************************");
        System.out.println("  SWE449 - Parallel Programming Project");
        System.out.println("  Topic: Shared Account - Thread Collaboration");
        System.out.println("         using wait() & notifyAll()");
        System.out.println("*************************************************************\n");

        SharedAccount sharedAccount = new SharedAccount("Family Account", 100.0);

        Thread depositor1 = new Thread(
                new Depositor(sharedAccount, 150.0, 3),
                "Depositor-Ali"
        );
        Thread depositor2 = new Thread(
                new Depositor(sharedAccount, 150.0, 3),
                "Depositor-Veli"
        );

        Thread withdrawer1 = new Thread(
                new Withdrawer(sharedAccount, 120.0, 2),
                "Withdrawer-Ayse"
        );
        Thread withdrawer2 = new Thread(
                new Withdrawer(sharedAccount, 120.0, 2),
                "Withdrawer-Fatma"
        );
        Thread withdrawer3 = new Thread(
                new Withdrawer(sharedAccount, 120.0, 2),
                "Withdrawer-Zeynep"
        );

        System.out.println("-------------------------------------------------------------");
        System.out.println("  STARTING THREADS...");
        System.out.println("-------------------------------------------------------------\n");

        withdrawer1.start();
        withdrawer2.start();
        withdrawer3.start();

        depositor1.start();
        depositor2.start();

        try {
            depositor1.join();
            depositor2.join();
            withdrawer1.join();
            withdrawer2.join();
            withdrawer3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("!! Main thread interrupted.");
        }

        System.out.println("\n=============================================================");
        System.out.println("  ALL TRANSACTIONS COMPLETED");
        System.out.println("=============================================================");
        System.out.printf("  Initial Balance    :   100.00 TL%n");
        System.out.printf("  Total Deposited    : + 900.00 TL  (2 threads x 3 txns x 150 TL)%n");
        System.out.printf("  Total Withdrawn    : - 720.00 TL  (3 threads x 2 txns x 120 TL)%n");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("  Expected Balance   :   280.00 TL%n");
        System.out.printf("  Actual Balance     :   %.2f TL%n", sharedAccount.getBalance());
        System.out.println("-------------------------------------------------------------");

        if (Math.abs(sharedAccount.getBalance() - 280.0) < 0.01) {
            System.out.println("  RESULT: SUCCESS - Synchronization is working correctly!");
        } else {
            System.out.println("  RESULT: ERROR - Synchronization issue detected!");
        }

        System.out.println("=============================================================");
    }
}
