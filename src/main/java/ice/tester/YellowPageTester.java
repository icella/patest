package ice.tester;

import com.google.common.collect.Maps;
import ice.api.upcapi.YellowPageApi;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-8-18.
 */
public class YellowPageTester implements Runnable{
    private Logger logger= Logger.getLogger(this.getClass());
    public static final String PHONE = "phone";
    public static final String ORIG_TAG = "orig_tag";

    private Integer id;
    private CountDownLatch latcher;

    public YellowPageTester(Integer id, CountDownLatch latcher) {
        this.id = id;
        this.latcher = latcher;
    }

    @Override public void run() {
        try {
            Map<String, String> inputParams = readOrigNumberFromFile();
            if (inputParams == null)
                return;

            for(Map.Entry<String, String> entry : inputParams.entrySet()){
                new YellowPageApi(entry.getKey()).runRequest();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            latcher.countDown();
        }
    }

    private Map<String, String> readOrigNumberFromFile() {
        Map<String, String> inputParams = Maps.newHashMap();

        LineIterator lineIterator = null;
        try {
            File file = new File(getClass().getClassLoader().getResource("yellowpage_100.txt").getFile());
            lineIterator = FileUtils.lineIterator(file);

        } catch (IOException e) {
            logger.info("Read file error! ", e);
        }

        if(lineIterator == null){
            return null;
        }

        while(lineIterator.hasNext()){
            String line = lineIterator.next().trim();
            if(line.length() == 0){
                continue;
            }

            String[] lineSplited = line.split("\t", -1);

            String phoneNumber = lineSplited[0];
            if(!StringUtils.isNumeric(phoneNumber)){
                continue;
            }

            inputParams.put(PHONE, lineSplited[0]);
            inputParams.put(ORIG_TAG, lineSplited[1]);
        }
        return inputParams;
    }
}
