package ice.tester;

import com.google.common.collect.Lists;
import ice.api.upcapi.DunNumberMarkApi;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-8-25.
 */
public class DunNumberMarkTester implements Runnable{
    private Logger logger= Logger.getLogger(this.getClass());
    private Integer id;
    private CountDownLatch latcher;

    public DunNumberMarkTester(Integer id, CountDownLatch latcher) {
        this.id = id;
        this.latcher = latcher;
    }

    @Override public void run() {
        try {
            List<String>  fileContent = getRecordsFromFile();
            List<List<String>> fileList = Lists.partition(fileContent, 100);
            for(List<String> file : fileList){
                new DunNumberMarkApi(file).runRequest();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            latcher.countDown();
        }
    }

    private List<String> getRecordsFromFile() {
        try {
            return FileUtils
                .readLines(new File(getClass().getClassLoader().getResource("yellowpage_10000.txt").getFile()));
        } catch (IOException e) {
            logger.error(e);
        }

        return null;
    }
}
