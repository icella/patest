package ice.utils;

import com.google.common.io.Files;
import ice.AppConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by lla on 17-7-3.
 */
public class PostUtil {
    protected final static Logger logger = Logger.getLogger(PostUtil.class);

    private String url;

    public PostUtil(String url) {
        this.url = url;
    }

    public String httpGet(String param) throws Exception{
        StopWatch stopWatch = new Log4JStopWatch("statusCode");

        String body;
        try {
            body = HttpClientUtil.getInstance()
                .get(AppConfig.getInstance().getStringProperty(AppConfig.UPC_URL) + url + "&" + param, null);
            ParamPreConditions.checkNotNull(body, "HTTP Exception!!!");

            String statusCode = "200";
            if(StringUtils.isNumeric(body)){
                statusCode = body;
            }

            if(!statusCode.equals("200")){
                logger.info("response status code : " + statusCode);
            }

            if(AppConfig.getInstance().getBooleanProperty(AppConfig.PRINT_INPUT_PRMS)){
                logger.info("rs : " + body);
            }
        } finally {
            stopWatch.stop();
        }

        return body;
    }

    public String httpPost(Map<String, String> paramMap, Map<String, File> filePrms) throws Exception {
        HttpPost post = builPostEntity(paramMap, getUrl(), filePrms);
        StopWatch stopWatch = new Log4JStopWatch("statusCode");

        String body;
        try {
            body = HttpClientUtil.getInstance().postStr(post);
            ParamPreConditions.checkNotNull(body, "HTTP Exception!!!");

            String statusCode = "200";
            if(StringUtils.isNumeric(body)){
                statusCode = body;
            }

            logger.info("response status code : " + statusCode);
            if(AppConfig.getInstance().getBooleanProperty(AppConfig.PRINT_INPUT_PRMS)){
                logger.info("rs : " + body);
            }
        } finally {
            stopWatch.stop();
        }

        return body;
    }

    public HttpPost builPostEntity(Map<String, String> params, String  url, Map<String, File> filePrms) {
        HttpPost post = new HttpPost(AppConfig.getInstance().getStringProperty(AppConfig.UPC_URL) + url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (String key : params.keySet()) {
            builder.addPart(key, new StringBody(params.get(key), ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8")));
        }

        if(filePrms != null && filePrms.size() > 0){
            for(Map.Entry<String, File> entry : filePrms.entrySet()){
                String fileName = entry.getKey();
                File tempFile = entry.getValue();
                if(tempFile.isFile()){
                    builder.addPart(fileName, new FileBody(tempFile, ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8")));
                }
            }
        }

        post.setEntity(builder.build());

        return post;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
