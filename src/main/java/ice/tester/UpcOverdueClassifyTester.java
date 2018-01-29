package ice.tester;

import ice.api.upcapi.UpcOverdueClassify;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 逾期接口
 * Created by lla on 17-4-18.
 */
public class UpcOverdueClassifyTester extends MainWorker {
    public UpcOverdueClassifyTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new UpcOverdueClassify(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("query_type", "=", "90000");
    }
}
