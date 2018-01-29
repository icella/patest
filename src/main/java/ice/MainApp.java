package ice;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import ice.tester.TesterFactory;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by lla on 17-4-7.
 */
public class MainApp {
    private final static Logger logger = Logger.getLogger(MainApp.class);

    private static final ExecutorService mainExecutor;
    private static List<String> apiNames;
    private static int vusers;
    private static long totalTimeout;


    static {
        ThreadFactory thFactory = new ThreadFactoryBuilder().setNameFormat("api-test-%d").setDaemon(true).build();
        mainExecutor = Executors.newCachedThreadPool(thFactory);

        AppConfig config = AppConfig.getInstance();
        apiNames = config.getListProperty(AppConfig.API_NAMES);
        vusers = config.getIntProperty(AppConfig.V_USER);
        totalTimeout = config.getLongProperty(AppConfig.TOTAL_TIMEOUT);
    }

    public static void main(String[] args) {
        try {
            logger.info("====[ Startup ]====");
            exec();
        } finally {
            logger.info("====[ All finished ]====");
            System.exit(0);
        }
    }

    private static void exec() {
        CountDownLatch latch = new CountDownLatch(vusers * apiNames.size());

        for (int i = 1; i < vusers + 1; i++) {
            for (String clzName : apiNames) {
                try {
                    mainExecutor.submit(TesterFactory.getInstance().createTester(clzName, i, latch));
                } catch (Exception e) {
                    latch.countDown();
                    logger.error(clzName + " submit failed.", e);
                }
            }
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("CountDownLatcher can't await.", e);
        }

        mainExecutor.shutdown();
        try {
            mainExecutor.awaitTermination(totalTimeout, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ExecutorService createExecutor(String tag) {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 50, 100, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat(tag).setDaemon(true).build(),
            new ThreadPoolExecutor.DiscardPolicy());
    }
}
