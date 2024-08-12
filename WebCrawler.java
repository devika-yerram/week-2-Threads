import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawler {

    private static final ConcurrentHashMap<String, String> crawledData = new ConcurrentHashMap<>();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static final AtomicInteger tasksCompleted = new AtomicInteger(0);

    static class CrawlerTask implements Callable<Void> {
        private final String url;

        public CrawlerTask(String url) {
            this.url = url;
        }

        @Override
        public Void call() {

            try {
                Thread.sleep(1000); // Simulate time taken to crawl
                String content = "Content of " + url; // Simulated content
                crawledData.put(url, content);
                System.out.println("Crawled URL: " + url);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task interrupted");
            }
            tasksCompleted.incrementAndGet();
            return null;
        }
    }

    public static void main(String[] args) {
 
        String[] urls = {"http://example.com", "http://example.org", "http://example.net"};

        for (String url : urls) {
            executorService.submit(new CrawlerTask(url));
        }


        executorService.shutdown();

        try {

            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Crawling completed. Total tasks completed: " + tasksCompleted.get());
        System.out.println("Crawled data:");
        for (String url : crawledData.keySet()) {
            System.out.println(url + ": " + crawledData.get(url));
        }
    }
}