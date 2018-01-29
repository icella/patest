package ice.api.upcapi;

import ice.AppConfig;
import ice.bean.dao.OrgLog;

import java.util.Map;

/**
 * Created by lla on 17-6-13.
 */
public class UpcModelScore extends UpcFinanceApi{

    public UpcModelScore(OrgLog record) {
        super(record);
    }

    @Override String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.MODELSCORE_URL);
    }

    @Override Map<String, String> buildRequestParams(OrgLog record) {
        return buildFinanceParam(record);
    }

    @Override public void run() {
        runRequest("phkjModelerScore");
    }
}
