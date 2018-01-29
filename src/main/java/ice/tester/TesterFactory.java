package ice.tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-4-18.
 */
public class TesterFactory {
    private static class LazyHolder {
        private static final TesterFactory _instance = new TesterFactory();
    }

    public static final TesterFactory getInstance() {
        return LazyHolder._instance;
    }

    private TesterFactory() {
    }

    public Runnable createTester(String clzName, Integer i, CountDownLatch latch)
        throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
        InstantiationException {
        Class clz = Class.forName("ice.tester." + clzName);
        Constructor constructor = clz.getConstructor(Integer.class, CountDownLatch.class);
        Runnable runnable = (Runnable) constructor.newInstance(i + 1, latch);

        return runnable;
    }
}
