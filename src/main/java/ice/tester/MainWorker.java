package ice.tester;

import com.google.common.collect.Lists;
import ice.AppConfig;
import ice.bean.dao.OrgLog;
import ice.utils.db.DBconnFactory;
import ice.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lla on 17-4-18.
 */
public abstract class MainWorker implements Runnable{
    private Logger logger= Logger.getLogger(this.getClass());
    private int id;
    private CountDownLatch latcher;

    protected int page_limit;
    protected Pager pager;
    protected Dao dao;
    protected int threadRepeatTimes;
    protected String beginDate;
    protected String endDate;

    protected String threadName;

    public MainWorker(Integer id, CountDownLatch latcher) {
        this.id = id;
        this.latcher = latcher;
    }

    public abstract void invoke(List<OrgLog> orgLogList);
    public abstract SqlExpressionGroup subConditon();
    public abstract void runMethod(OrgLog record);

    @Override public void run() {
        try {
            initialize();
            int counter = 0;
            String date = "";
            List<OrgLog> orgLogList = Lists.newArrayList();

            while (orgLogList.size() != page_limit && counter < 1000){
                date = randomDate();
                orgLogList = getOrgLogsFromDb(date);
                counter++;
            }

            StringBuffer logSb = new StringBuffer(threadName);
            logSb.append(" :: ").append(date).append(" :: ").append(orgLogList.size());
            logger.info(logSb);

            invoke(orgLogList);
        } catch (Exception e){
            logger.error(e);
        }
        finally {
            latcher.countDown();
        }
    }

    public void initialize() throws Exception{
        AppConfig config = AppConfig.getInstance();
        dao = DBconnFactory.getInstance().getDao();
        page_limit = config.getIntProperty(AppConfig.DB_PAGE_LIMIT);
        pager = dao.createPager(config.getIntProperty(AppConfig.DB_PAGE_START), page_limit);
        threadRepeatTimes = config.getIntProperty(AppConfig.THREAD_REPEAT_TIMES);
        beginDate = config.getStringProperty(AppConfig.DB_BEGIN_DATE);
        endDate = config.getStringProperty(AppConfig.DB_END_DATE);

        threadName = Thread.currentThread().getName() + id;
    }

    private String randomDate(){
        return TimeUtil.getRandomDate(beginDate, endDate);
    }

    private List<OrgLog> getOrgLogsFromDb(String date) {
        SqlExpression query_time = Cnd.exp("query_time", "=", date);
        SqlExpression op_result = Cnd.exp("op_result", "=", "200");
        Cnd conditon = Cnd.where(query_time).and(op_result).and(subConditon());

        List<OrgLog> orgLogList = null;
        try {
            TableName.set(AppConfig.getInstance().getProperty(AppConfig.ORGLOG_SUFFIX));
            orgLogList = dao.query(OrgLog.class, conditon, pager);
        } catch (Exception e) {
            logger.error("查询Orglog出错! ", e);
        } finally {
            TableName.clear();
        }

        return orgLogList;
    }

    public void multExec(List<OrgLog> orgLogList){
        for (int i = 0; i < threadRepeatTimes; i++) {
            for (int j = 0; j < orgLogList.size(); j++) {
                runMethod(orgLogList.get(j));
            }
        }
    }
}
