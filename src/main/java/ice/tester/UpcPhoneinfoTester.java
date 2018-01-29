package ice.tester;

import ice.api.upcapi.UpcPhoneinfo;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 电话信息接口
 */
public class UpcPhoneinfoTester extends MainWorker {
    public UpcPhoneinfoTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new UpcPhoneinfo(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("sub_query_type", ">", "93001").and("sub_query_type", "<", "93004");
    }
}
