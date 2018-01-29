package ice.tester;

import ice.api.util.HbaseReq;
import ice.bean.dao.OrgLog;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-6-12.
 */
public class HbaseReqTester extends MainWorker {
    public HbaseReqTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new HbaseReq(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return null;
    }
}
