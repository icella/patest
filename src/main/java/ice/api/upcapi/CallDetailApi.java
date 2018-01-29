package ice.api.upcapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ice.AppConfig;
import ice.bean.dao.QueryCallDetails;
import ice.utils.Base64;
import ice.utils.ParamPreConditions;
import ice.utils.PostUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lla on 17-7-3.
 */
public class CallDetailApi {
    protected final static Logger logger = Logger.getLogger(CallDetailApi.class);
    private List<QueryCallDetails> records;

    private String fileName;
    private String fileContent;

    public CallDetailApi(List<QueryCallDetails> records) {
        this.records = records;
    }

    public CallDetailApi(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public void runRequest() {
        StopWatch stopWatch = new Log4JStopWatch("Detail_Analysis");
        Map<String, String> paramMap = fileRequest();

        try {
            String body =
                new PostUtil(AppConfig.getInstance().getStringProperty(AppConfig.CALLDETAILANALYSISREPORT_URL))
                    .httpPost(paramMap, null);
//            logger.info(String.format("Result : %s", body));
            logger.info("Detail_Analysis" + " hit=" + isHit(body));
//            logger.info("Write pdf " + fileName + " :: " + writePdf(body));
            logger.info("Write Json " + fileName + " :: " + writeJson(body));
        } catch (Exception e) {
            logger.error("Detail_Analysis" + " :: ", e);
        } finally {
            stopWatch.stop();
        }
    }

    private Map<String, String> dbRequest() {
        String jsonStr = JSONObject.toJSONString(records);
        if(AppConfig.getInstance().getBooleanProperty(AppConfig.PRINT_INPUT_PRMS)){
            logger.info("Input size :: " + records.size() + " prms :: " + jsonStr);
        }

        //20170706042738623
        //20170414021537200
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("vkey", "20170414021537200");
        //        paramMap.put("apply_date", "2016-12-01");
        paramMap.put("call_details", jsonStr);

        return paramMap;
    }

    public Map<String, String> fileRequest(){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("vkey", "20170414021537200");
        paramMap.put("call_details", fileContent);

        return paramMap;
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

    private boolean writeJson(String content){
        if(StringUtils.isNotEmpty(content)){

            File file = new File(
                AppConfig.getInstance().getProperty(AppConfig.CALL_DETAIL_ANALYSIS_REPORT_PDF_PATH) + fileName
                    + ".json");
            try {
                FileUtils.write(file, content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }

    private boolean writePdf(String content){

        if(StringUtils.isNotEmpty(content)){
            try {
                String base64Str = JSONObject.parseObject(content).getString("data");

                if(StringUtils.isNotBlank(base64Str)){
                    byte[] bytes = Base64.decode(base64Str);
                    logger.info(bytes.length);

                    File file = new File(
                        AppConfig.getInstance().getProperty(AppConfig.CALL_DETAIL_ANALYSIS_REPORT_PDF_PATH) + fileName
                            + ".pdf");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }
}
