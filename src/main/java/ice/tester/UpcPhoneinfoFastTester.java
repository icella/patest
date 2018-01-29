package ice.tester;

import ice.api.upcapi.UpcPhoneinfoFast;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 快速电话信息接口
 */
public class UpcPhoneinfoFastTester extends MainWorker {
    public UpcPhoneinfoFastTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new UpcPhoneinfoFast(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        SqlExpressionGroup group =
            Cnd.exps("sub_query_type", "=", "93001").or("sub_query_type", "=", "93005");

        return group;
    }
}
