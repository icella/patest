package ice.api.upcapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ice.AppConfig;
import ice.utils.ParamPreConditions;
import ice.utils.PostUtil;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lla on 17-8-25.
 */
public class DunNumberMarkApi {
    protected final static Logger logger = Logger.getLogger(DunNumberMarkApi.class);
    private List<String> file;

    public DunNumberMarkApi(List<String> file) {
        this.file = file;
    }

    public void runRequest() {
        StopWatch stopWatch = new Log4JStopWatch("Dun_number_mark");
        String param = fileRequest();

        try {
            String body =
                new PostUtil(AppConfig.getInstance().getStringProperty(AppConfig.DUN_NUMBER_MARK_URL)).httpGet(param);

//            logger.info(String.format("Result : %s", body));
            logger.info("Detail_Analysis" + " hit=" + isHit(body));
        } catch (Exception e) {
            logger.error("Detail_Analysis" + " :: ", e);
        } finally {
            stopWatch.stop();
        }
    }

    public String fileRequest(){
        StringBuilder numbers = new StringBuilder();
        for(String number : this.file){
            numbers.append(",").append(number);
        }

        String number = numbers.substring(1, numbers.lastIndexOf(",") -1);

        return "vkey=20170414021537200&number=" + number;
    }

    protected String isHit(String body) {
        JSONObject firstJsonObject = JSON.parseObject(body);
        String resultCode = ParamPreConditions.checkNotNull(firstJsonObject.getString("result"), body);
        if (resultCode.equals("200")) {
            return "1";
        } else if (resultCode.equals("9999")) {
            return "Inner Exception !!! ";
        }

        return "0";
    }
}
