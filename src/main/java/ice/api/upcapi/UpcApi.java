package ice.api.upcapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ice.AppConfig;
import ice.api.PrmUtils;
import ice.bean.dao.OrgLog;
import ice.utils.ParamPreConditions;
import ice.utils.SecureUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lla on 17-4-18.
 */
public abstract class UpcApi {
    protected final static Logger logger = Logger.getLogger(UpcApi.class);

    protected OrgLog record;
    public UpcApi(OrgLog record) {
        this.record = record;
    }

    abstract String buildRequestUrl();
    abstract Map<String, String> buildRequestParams(OrgLog record);

    protected Map<String, String> buildAuthParams(){
        Map<String, String> params = new HashMap<>();

        long ptime = System.currentTimeMillis();
        params.put("pname", AppConfig.getInstance().getStringProperty(AppConfig.UPC_PNAME));
        params.put("ptime", String.valueOf(ptime));
        try {
            params.put("vkey", getmd5Str(ptime, AppConfig.getInstance().getStringProperty(AppConfig.UPC_VKEY)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  params;
    }

    /**
     * blacklist, overdueClassify, loanClassify
     * @param record
     * @return
     */
    protected Map<String, String> buildFinanceParam(OrgLog record){
        Map<String, String> params = buildAuthParams();

        PrmUtils.useUid(params, record);
        params.put("phone", PrmUtils.useMd5(record.getPhone()));
        params.put("imsi", PrmUtils.useMd5(record.getMobileImsi()));
        params.put("imei", PrmUtils.useMd5(record.getMobileImei()));

        if(AppConfig.getInstance().getBooleanProperty(AppConfig.PRINT_INPUT_PRMS)){
            logger.info(String.format("Input args: {idcard: %s, name: %s, phone: %s, imsi: %s, imei: %s}",
                record.getIdCard().substring(0, 18), record.getName(),record.getPhone(),
                record.getMobileImsi(), record.getMobileImei()));
        }

        return params;
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
