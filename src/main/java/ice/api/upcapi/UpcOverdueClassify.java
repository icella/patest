package ice.api.upcapi;

import ice.AppConfig;
import ice.bean.dao.OrgLog;

import java.util.Map;

/**
 * upc_portal overdueClassify
 * Created by lla on 17-4-18.
 */
public class UpcOverdueClassify  extends UpcFinanceApi{
    public UpcOverdueClassify(OrgLog record) {
        super(record);
    }

    @Override String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.OVERDUECLASSIFY_URL);
    }

    @Override Map<String, String> buildRequestParams(OrgLog record) {
        return buildFinanceParam(record);
    }

    @Override public void run() {
       runRequest("overdueClassify");
    }
}
