package ice.tester;

import ice.api.upcapi.UpcLogStat;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 贷款意向(被其他家查询日志统计)接口
 * Created by lla on 17-6-13.
 */
public class UpcLogStatTester extends MainWorker {
    public UpcLogStatTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override
    public void runMethod(OrgLog record){
        new UpcLogStat(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("sub_query_type","=", "92000");
    }
}
