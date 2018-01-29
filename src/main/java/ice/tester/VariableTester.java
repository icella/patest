package ice.tester;

import ice.api.upcapi.VariableApi;
import ice.bean.dao.OrgLog;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 模型分接口
 * Created by lla on 17-6-13.
 */
public class VariableTester extends MainWorker {
    public VariableTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        multExec(orgLogList);
    }

    @Override public void runMethod(OrgLog record) {
        new VariableApi(record).run();
    }

    @Override public SqlExpressionGroup subConditon() {
        return Cnd.exps("query_type", "=","240000").and("op_result", "=", "200");
    }
}
