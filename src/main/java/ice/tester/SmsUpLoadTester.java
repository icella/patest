package ice.tester;

import com.google.common.collect.Lists;
import ice.AppConfig;
import ice.bean.dao.SmsQianCheng;
import ice.utils.PostUtil;
import ice.utils.db.DBconnFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-9-21.
 */
public class SmsUpLoadTester implements Runnable{
    private Logger logger= Logger.getLogger(this.getClass());
    public static final Integer PAGE_SIZE = 100;

    private Integer id;
    private CountDownLatch latcher;
    protected Dao dao;
    private int smsCounter = 0;

    public SmsUpLoadTester(Integer id, CountDownLatch latcher) {
        this.id = id;
        this.latcher = latcher;
        initialize();
    }

    @Override public void run() {
        int totalPages = smsCounter / PAGE_SIZE + 1;
        for (int i = 1; i < totalPages + 1; i++) {
            Pager pager = dao.createPager(i, PAGE_SIZE);
            List<SmsQianCheng> records = dao.query(SmsQianCheng.class, null, pager);
            List<String> rawSmsList = Lists.newArrayList();
            for(SmsQianCheng record : records){
                rawSmsList.add(record.getSms_text());
            }

//            String smsStr = StringUtils.join(rawSmsList, "\n");
            Map<String, String> prmMap = new HashMap<String, String>(){
                {
                    put("vkey", "20170414021537200");
                }
            };

            File text = null;
            try {
                text = File.createTempFile("sms", "txt");
                FileUtils.writeLines(text, rawSmsList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<String, File> fileMap = new HashMap<>();
            fileMap.put("tempFile", text);

            try {
                String body = new PostUtil(AppConfig.getInstance().getStringProperty(AppConfig.UPLOAD_TEXT_URL))
                    .httpPost(prmMap, fileMap);
                logger.info(body);
            } catch (Exception e) {
                logger.error("Upload SMS " + " :: ", e);
            } finally {
                text.delete();
            }
        }
    }

    // Get sms from sms_qiancheng random.
    // 10 seconds later, request sms parser


    private void initialize() {
        AppConfig config = AppConfig.getInstance();
        dao = DBconnFactory.getInstance().getDao();

        smsCounter = dao.count(SmsQianCheng.class);
    }
}
