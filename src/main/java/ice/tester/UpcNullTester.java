package ice.tester;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ice.AppConfig;
import ice.utils.HttpClientUtil;
import ice.utils.ParamPreConditions;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-7-5.
 */
public class UpcNullTester implements Runnable {
    private Logger logger= Logger.getLogger(this.getClass());
    private Integer id;
    private CountDownLatch latcher;

    public UpcNullTester(Integer id, CountDownLatch latcher) {
        this.id = id;
        this.latcher = latcher;
    }

    @Override public void run() {
        try {
            String url = AppConfig.getInstance().getStringProperty(AppConfig.UPC_URL) +
                AppConfig.getInstance().getStringProperty(AppConfig.NULLRESULT_URL);
            Integer threadRepeatTimes = AppConfig.getInstance().getIntProperty(AppConfig.THREAD_REPEAT_TIMES);
            Integer page_limit = AppConfig.getInstance().getIntProperty(AppConfig.DB_PAGE_LIMIT);

            for (int j = 0; j < threadRepeatTimes; j++) {
                for (int i = 0; i < page_limit; i++) {
                    StopWatch stopWatch = new Log4JStopWatch("nullResult");
                    try {
                        String body = HttpClientUtil.getInstance().get(url, null);
                        if(AppConfig.getInstance().getBooleanProperty(AppConfig.PRINT_INPUT_PRMS)) {
                            logger.info(String.format("Result : %s  %s", body, body.length()));
                        }

                        logger.info("nullResult" + " hit=" + isHit(body));
                    } catch (Exception e) {
                        logger.error("nullResult" + " :: ", e);
                    }finally {
                        stopWatch.stop();
                    }
                }
            }
        } catch (Exception e){
            logger.error(e);
        } finally {
            latcher.countDown();
        }
    }

    protected String isHit(String body) {
        JSONObject firstJsonObject = JSON.parseObject(body);
        String resultCode = ParamPreConditions.checkNotNull(firstJsonObject.getString("result"), body);
        if (resultCode.equals("2")) {
            return "1";
        } else if (resultCode.equals("9999")) {
            return "Inner Exception !!! ";
        }

        return "0";
    }
}
