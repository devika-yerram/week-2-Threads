import java.util.*;

public class ThreadStateLogger {

    static class MyThread extends Thread {
        private int n;

        public MyThread(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            for (int i = 0; i <= n; i++) {
                System.out.println("Number: " + i);
                try {
                    // Simulate some work with a sleep
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted");
                }
            }
        }
    }

    public static void main(String[] args) {

        MyThread thread = new MyThread(5);

        System.out.println("Thread state before starting: " + thread.getState());

        thread.start();

        System.out.println("Thread state after starting: " + thread.getState());

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted");
        }

        System.out.println("Thread state after completion: " + thread.getState());
    }
}