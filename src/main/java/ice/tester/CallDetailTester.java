package ice.tester;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ice.AppConfig;
import ice.api.upcapi.CallDetailApi;
import ice.bean.dao.QueryCallDetails;
import ice.utils.RandomUtil;
import ice.utils.db.DBconnFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-7-3.
 */
public class CallDetailTester implements Runnable{
    private Logger logger= Logger.getLogger(this.getClass());
    private Integer id;
    private CountDownLatch latcher;

    protected Dao dao;
    protected int threadRepeatTimes;
    protected String threadName;
    private int lid;

    public CallDetailTester(Integer id, CountDownLatch latcher) {
        this.id = id;
        this.latcher = latcher;
    }

//    @Override
    public void run1() {
        initialize();

        try {
            List<QueryCallDetails> historyList = getRecordsFromDb();
            StringBuffer logSb = new StringBuffer(threadName);
            logSb.append(" :: ").append(lid).append(" :: ").append(historyList.size());
            logger.info(logSb);

            for (int i = 0; i < threadRepeatTimes; i++) {
                new CallDetailApi(historyList).runRequest();
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            latcher.countDown();
        }
    }

    private List<QueryCallDetails> getRecordsFromDb() {
        lid = RandomUtil.genRandomNumber(1,240);

        return dao.query(QueryCallDetails.class, Cnd.where("lid", "=", lid));
    }

    private void initialize() {
        AppConfig config = AppConfig.getInstance();
        dao = DBconnFactory.getInstance().getDao();
        threadRepeatTimes = config.getIntProperty(AppConfig.THREAD_REPEAT_TIMES);

        threadName = Thread.currentThread().getName() + id;
    }

    @Override public void run() {
        try {
            Map<String, String> files = getRecordsFromFile();

            for(Map.Entry<String, String> entry : files.entrySet()){
                new CallDetailApi(entry.getKey(), entry.getValue()).runRequest();
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            latcher.countDown();
        }
    }

    private Map<String, String> getRecordsFromFile(){
        try {
            List<File> files = Lists.newArrayList(
                FileUtils.listFiles(new File("/home/lla/Desktop/json_test"), new String[] {"txt"}, true));

            Map<String, String> records = Maps.newHashMap();
            for(File file : files){
                String fileAllName = file.getName();
                String fileName = fileAllName.substring(0, fileAllName.indexOf("."));
                String fileContent = FileUtils.readFileToString(file);
                logger.info(fileName + " :: " + String.valueOf(fileContent.length()));

                records.put(fileName, fileContent);
            }

            return records;
        } catch (IOException e) {
            logger.error(e);

            return null;
        }
    }
}
