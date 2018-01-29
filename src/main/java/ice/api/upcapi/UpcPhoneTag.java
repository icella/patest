package ice.api.upcapi;

import ice.AppConfig;
import ice.bean.dao.OrgLog;
import ice.utils.PostUtil;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.Map;

/**
 * Created by lla on 17-6-22.
 */
public class UpcPhoneTag extends UpcApi implements Runnable{

    public UpcPhoneTag(OrgLog record) {
        super(record);
    }

    @Override String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.PHONETAG_URL);
    }

    @Override Map<String, String> buildRequestParams(OrgLog record) {
        Map<String, String> params = buildAuthParams();
        params.put("phone", record.getPhone());
        params.put("imsi", record.getMobileImsi());
        params.put("imei", record.getMobileImei());

        return params;
    }

    @Override public void run() {
        StopWatch stopWatch = new Log4JStopWatch("phonetag");
        Map<String, String> paramMap = buildRequestParams(record);

        try{
            String body = new PostUtil(buildRequestUrl()).httpPost(paramMap, null);

            logger.info("UPC_phonetag hit=" + isHit(body));
        } catch (Exception e){
            logger.error("UPC_phonetag", e);
        } finally {
            stopWatch.stop();
        }
    }
}
