package ice.api.thirdpart;

import ice.bean.ContactCommonRs;
import ice.bean.ContactPair;
import ice.api.thirdpart.qihu.ContactCommon;
import ice.utils.ParamPreConditions;

/**
 * Created by lla on 17-4-24.
 */
public class QihuCommon extends QihuAPI {

    public QihuCommon(ContactPair record, String threadName, int id) {
        super(record, threadName, id);
    }

    @Override String exec() {
        try {
            ContactCommonRs jsonStr =
                new ContactCommon(record.getPa(), record.getPb(), record.getPad(), record.getPbd()).call();
            ParamPreConditions.checkNotNull(jsonStr, record.toString() + " is null");

            return jsonStr.getJsonData();
        } catch (Exception e) {
            logger.error(record.toString() + " : ", e);
        }

        return null;
    }
}
