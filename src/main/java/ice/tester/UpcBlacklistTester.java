package ice.tester;

import ice.api.upcapi.UpcBlacklist;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 黑名单接口
 * Created by lla on 17-4-18.
 */
public class UpcBlacklistTester extends MainWorker {
    public UpcBlacklistTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new UpcBlacklist(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("query_type", "=", "40000");
    }
}
