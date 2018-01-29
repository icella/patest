package ice.api.upcapi;

import ice.AppConfig;
import ice.bean.dao.OrgLog;

import java.util.Map;

/**
 * Created by lla on 17-6-13.
 */
public class UpcPhkjModelScore extends UpcFinanceApi{

    public UpcPhkjModelScore(OrgLog record) {
        super(record);
    }

    @Override String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.PHKJMODELERSCORE_URL);
    }

    @Override Map<String, String> buildRequestParams(OrgLog record) {
        return buildFinanceParam(record);
    }

    @Override public void run() {
        runRequest("phkjmodelerscore");
    }
}
