public class TaskScheduler {

    static class Task extends Thread {
        private String taskName;
        private Task dependentTask;

        public Task(String taskName, Task dependentTask) {
            this.taskName = taskName;
            this.dependentTask = dependentTask;
        }

        @Override
        public void run() {
            try {
                if (dependentTask != null) {
                    dependentTask.join();
                }

                System.out.println(taskName + " started.");
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println(taskName + " is yielding to other tasks.");
                Thread.yield();

                Thread.sleep((long) (Math.random() * 2000));
                System.out.println(taskName + " completed.");
            } catch (InterruptedException e) {
                System.err.println(taskName + " interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        Task task1 = new Task("Task 1", null);
        Task task2 = new Task("Task 2", task1);
        Task task3 = new Task("Task 3", task2);
        Task task4 = new Task("Task 4", task2);

        task1.start();
        task2.start();
        task3.start();
        task4.start();

        try {
            // Wait for all tasks to complete
            task1.join();
            task2.join();
            task3.join();
            task4.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted.");
        }

        System.out.println("All tasks completed.");
    }
}