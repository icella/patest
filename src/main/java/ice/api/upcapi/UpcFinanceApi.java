package ice.api.upcapi;

import ice.bean.dao.OrgLog;
import ice.utils.PostUtil;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.Map;

/**
 * upc_portal blacklist, overdueClassify, loanClassify
 * Created by lla on 17-4-18.
 */
public abstract class UpcFinanceApi extends UpcApi implements Runnable {

    public UpcFinanceApi(OrgLog record) {
        super(record);
    }

    protected void runRequest(String tag){
        StopWatch stopWatch = new Log4JStopWatch(tag);
        Map<String, String> paramMap = buildRequestParams(record);

        try {
            String body = new PostUtil(buildRequestUrl()).httpPost(paramMap, null);
            logger.info("UPC_" + tag + " hit=" + isHit(body));
        } catch (Exception e) {
            logger.error("UPC_" + tag + " :: ", e);
        } finally {
            stopWatch.stop();
        }
    }
}
