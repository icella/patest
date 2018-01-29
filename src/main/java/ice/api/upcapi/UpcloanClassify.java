package ice.api.upcapi;

import ice.AppConfig;
import ice.bean.dao.OrgLog;

import java.util.Map;

/**
 * upc_portal loanClassify
 * Created by lla on 17-4-18.
 */
public class UpcloanClassify extends UpcFinanceApi{
    public UpcloanClassify(OrgLog record) {
        super(record);
    }

    @Override String buildRequestUrl() {
        return AppConfig.getInstance().getStringProperty(AppConfig.LOANCLASSIFY_URL);
    }

    @Override Map<String, String> buildRequestParams(OrgLog record) {
        return buildFinanceParam(record);
    }

    @Override public void run() {
        runRequest("loanClassify");
    }
}
