package ice.api.upcapi;

import ice.AppConfig;
import ice.bean.dao.OrgLog;

import java.util.Map;

/**
 * Created by lla on 17-6-13.
 */
public class VariableApi extends UpcFinanceApi{

    public VariableApi(OrgLog record) {
        super(record);
    }

    @Override String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.VARIABLEAPi_URL);
    }


    @Override Map<String, String> buildRequestParams(OrgLog record) {
        Map<String, String> params = buildAuthParams();
        params.put("imei", record.getMobileImei());
        params.put("name", record.getName());
        params.put("idCard", record.getIdCard().substring(0, 18));
        params.put("version", AppConfig.getInstance().getStringProperty(AppConfig.VARIABLEA_VERSION));

        return params;
    }

    @Override public void run() {
        runRequest("variable");
    }
}
