package ice.api.thirdpart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ice.bean.ContactPair;
import ice.bean.dao.PhoneTag;
import ice.utils.ParamPreConditions;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

/**
 * Created by lla on 17-4-24.
 */
public abstract class QihuAPI implements Runnable{
    protected final static Logger logger = Logger.getLogger(QihuAPI.class);
    protected ContactPair record;
    protected String threadName;
    protected int id;

    abstract String exec();

    public QihuAPI(ContactPair record , String threadName, int id) {
        this.record = record;
        this.threadName = threadName;
        this.id = id;
    }

    @Override public void run() {
        StopWatch stopWatch = new Log4JStopWatch("360Tag");

        try {
            String json = exec();
            logger.info(json);
            if(StringUtils.isNotEmpty(json)){
                PhoneTag out = hasPhoneTag(record.toString(), json);
                logger.info(out.toString());
            }
        } finally {
            stopWatch.stop();
        }
    }

    protected PhoneTag hasPhoneTag(String phoneStr, String body) {
        JSONObject object = JSON.parseObject(body);
        String resultStr = ParamPreConditions.checkNotNull(object.getString("result"), body);
//        logger.info(body);

        Integer result = Integer.parseInt(resultStr);

        PhoneTag tag = new PhoneTag();
        tag.setPhone(phoneStr).setTag(threadName).setId(id).setHit(0);
        if (result == 0) {
            String data = object.getJSONObject("data").getString("statA");

            if (!data.equals("false")) {
                tag.setHit(1);
            }
        }

        return tag;
    }
}
