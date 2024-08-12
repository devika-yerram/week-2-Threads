import java.util.Random;

public class SensorSimulation {

    static class Sensor extends Thread {
        private static final int INTERVAL = 2000; // Interval in milliseconds

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                int randomData = random.nextInt(100); // Generate random data between 0 and 99
                System.out.println("Printing random data: " + randomData);
                try {
                    Thread.sleep(INTERVAL); // Sleep for the specified interval
                } catch (InterruptedException e) {
                    System.err.println("Sensor thread interrupted");
                    return; // Exit the thread if interrupted
                }
            }
        }
    }

    public static void main(String[] args) {

        Thread[] sensors = new Thread[5];

        for (int i = 0; i < sensors.length; i++) {
            sensors[i] = new Sensor();
            sensors[i].start();
        }

        try {
            for (Thread sensor : sensors) {
                sensor.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted");
        }
    }
}