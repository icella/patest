package ice.tester;

import ice.api.upcapi.UpcloanClassify;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *  贷款接口
 * Created by lla on 17-4-18.
 */
public class UpcloanClassifyTester  extends MainWorker {
    public UpcloanClassifyTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new UpcloanClassify(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("query_type", "=", "91000");
    }
}
