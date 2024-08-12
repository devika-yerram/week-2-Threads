import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    static class DataQueue {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity = 10;

        public synchronized void produce(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                wait();
            }
            queue.add(item);
            System.out.println("Produced: " + item);
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            int item = queue.poll();
            System.out.println("Consumed: " + item);
            notifyAll();
            return item;
        }
    }

    // Producer thread class
    static class Producer extends Thread {
        private final DataQueue dataQueue;

        public Producer(DataQueue dataQueue) {
            this.dataQueue = dataQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    dataQueue.produce(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer thread class
    static class Consumer extends Thread {
        private final DataQueue dataQueue;

        public Consumer(DataQueue dataQueue) {
            this.dataQueue = dataQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    dataQueue.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        DataQueue dataQueue = new DataQueue();

        Producer producer1 = new Producer(dataQueue);
        Producer producer2 = new Producer(dataQueue);
        Consumer consumer1 = new Consumer(dataQueue);
        Consumer consumer2 = new Consumer(dataQueue);

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        try {
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}