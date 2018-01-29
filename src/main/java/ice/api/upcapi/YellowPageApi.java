package ice.api.upcapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ice.AppConfig;
import ice.utils.ParamPreConditions;
import ice.utils.PostUtil;
import ice.utils.SecureUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lla on 17-8-18.
 */
public class YellowPageApi{

    protected final static Logger logger = Logger.getLogger(YellowPageApi.class);

    private String phoneNumber;

    public YellowPageApi(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void runRequest() {
        StopWatch stopWatch = new Log4JStopWatch("Detail_Analysis");
        Map<String, String> paramMap = buildParams();

        try {
            String body =
                new PostUtil(AppConfig.getInstance().getStringProperty(AppConfig.YELLOWPAGE_URL)).httpPost(paramMap, null);
            logger.info(String.format("Result : %s", body));
            logger.info("YellowPage" + " hit=" + isHit(body));
        } catch (Exception e) {
            logger.error("YellowPage" + " :: ", e);
        } finally {
            stopWatch.stop();
        }
    }

    protected Map<String, String> buildParams(){
        Map<String, String> params = new HashMap<>();

        long ptime = System.currentTimeMillis();
        params.put("pname", AppConfig.getInstance().getStringProperty(AppConfig.UPC_PNAME));
        params.put("ptime", String.valueOf(ptime));
        params.put("phone", phoneNumber);

        try {
            params.put("vkey", getmd5Str(ptime, AppConfig.getInstance().getStringProperty(AppConfig.UPC_VKEY)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  params;
    }

    private static String getmd5Str(long ptime, String key) throws Exception {
        return SecureUtil.md5(key + "_" + ptime + "_" + key);
    }

    protected String isHit(String body){
        if(body == null){
            return "0";
        }

        if(StringUtils.isNumeric(body)){
            return "0";
        }

        JSONObject firstJsonObject = JSON.parseObject(body);
        String resultCode = ParamPreConditions.checkNotNull(firstJsonObject.getString("result"), body);
        if(resultCode.equals("0")){
            return "1";
        } else if (resultCode.equals("9999")){
            return "Inner Exception !!! ";
        }

        return "0";
    }
}
