package ice.api.upcapi;

import ice.AppConfig;
import ice.api.PrmUtils;
import ice.bean.dao.OrgLog;
import ice.utils.PostUtil;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.Map;

/**
 * upc_portal phoneinfo
 * Created by lla on 17-4-17.
 */
public class UpcPhoneinfoFast extends UpcApi implements Runnable{
    public static String SPLIT_COMMA = ",";

    public UpcPhoneinfoFast(OrgLog record) {
        super(record);
    }

    @Override public void run() {
        StopWatch stopWatch = new Log4JStopWatch("phoneinfoFast");
        Map<String, String> paramMap = buildRequestParams(record);

        try {
            String body = new PostUtil(buildRequestUrl()).httpPost(paramMap, null);

            logger.debug("UPC_phoneinfoFast" + " :: " + body);
            logger.info("UPC_phoneinfoFast hit=" + isHit(body));
            if(logger.isDebugEnabled()){
                logger.debug("UPC_phoneinfoFast" + " :: " + body);
            }
        } catch (Exception e) {
            logger.error("UPC_phoneinfoFast :: ", e);
        } finally {
            stopWatch.stop();
        }
    }

    @Override public String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.PHONEINFO_FAST_URL);
    }

    @Override Map<String, String> buildRequestParams(OrgLog record) {
        Map<String, String> params = buildAuthParams();

        PrmUtils.useUid(params, record);
        String contactMain = record.getPhone() + SPLIT_COMMA + record.getMobileImsi() + SPLIT_COMMA + record.getMobileImei();
        params.put("contactMain", contactMain);
        params.put("contacts", record.getBank());

        return params;
    }
}
