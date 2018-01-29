package ice.api;

import ice.AppConfig;
import ice.bean.dao.OrgLog;
import ice.utils.SecureUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by lla on 17-6-22.
 */
public class PrmUtils {
    private final static Logger logger = Logger.getLogger(PrmUtils.class);

    public static void useUid(Map<String, String> params, OrgLog record){
        int uidTag = AppConfig.getInstance().getIntProperty(AppConfig.USE_UID);
        switch (uidTag){
            case 0:
                params.put("idCard", record.getIdCard().substring(0, 18));
                params.put("name", record.getName());
                break;
            case 1:
                params.put("uid", record.getName() + record.getIdCard().substring(0,18));
                break;
            case 2:
                try {
                    String uid = SecureUtil.md5(record.getName()+ record.getIdCard().substring(0,18));
                    params.put("uid", uid);
                } catch (Exception e) {
                    logger.info("Md5 failed! " + record.toString(), e);
                }
                break;
        }
    }

    public static String useMd5(String param){
        if(StringUtils.isBlank(param)){
            return param;
        }

        if(AppConfig.getInstance().getBooleanProperty(AppConfig.USE_MD5)){
            try {
                return SecureUtil.md5(param);
            } catch (Exception e) {
                return param;
            }
        }

        return param;
    }
}
