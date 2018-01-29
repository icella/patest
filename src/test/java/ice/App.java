package ice;

import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {
    private final static Logger logger = Logger.getLogger(App.class);

    public static void mainTest( String[] args ) {
        logger.info("Welcome to Perf4j");
        App app = new App();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 500; i++) {
            executor.submit(new ContactMethod("phone"));
            executor.submit(new ContactMethod("email"));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        app.contact("email");
//        app.contact("phone");
    }

    static class ContactMethod implements Runnable{
        private String method;

        public ContactMethod(String method) {
            this.method = method;
        }

        public void run() {
            StopWatch stopWatch = new Log4JStopWatch(method);
            try {
                Thread.sleep((long)(Math.random() *5000L));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(method);
            stopWatch.stop();
        }
    }


    private void contact(String type){
        StopWatch stopWatch = new Log4JStopWatch(type);
        try {
            Thread.sleep((long)(Math.random() * 1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(type);
        stopWatch.stop();

    }
}
