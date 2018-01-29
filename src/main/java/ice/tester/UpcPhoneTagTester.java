package ice.tester;

import ice.api.upcapi.UpcPhoneTag;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 号码标记接口
 * Created by lla on 17-6-22.
 */
public class UpcPhoneTagTester extends MainWorker {

    public UpcPhoneTagTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new UpcPhoneTag(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("sub_query_type", "=", "60000");
    }
}
