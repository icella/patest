package ice.api.thirdpart;

import ice.bean.ContactPair;
import ice.bean.ContactTagRs;
import ice.api.thirdpart.qihu.ContacTag;
import ice.utils.ParamPreConditions;

/**
 * 360 tag API
 */
public class QihuTag extends QihuAPI {

    public QihuTag(ContactPair record, String threadName, int id) {
        super(record, threadName, id);
    }

    @Override String  exec() {
        try {
            ContactTagRs jsonStr = new ContacTag(record.getPad(), record.getSad(), record.getEad(),
                record.getPbd(), record.getSbd(), record.getEbd()).call();
            ParamPreConditions.checkNotNull(jsonStr, record.toString() + " is null");

            return jsonStr.getJsonData();
        } catch (Exception e) {
            logger.error(record.toString() + " : ", e);
        }

        return null;
    }
}
