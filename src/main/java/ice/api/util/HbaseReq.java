package ice.api.util;

import ice.AppConfig;
import ice.bean.dao.OrgLog;
import ice.utils.hbase.*;
import ice.utils.pingan.identify.Identify;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import java.util.List;

/**
 * Created by lla on 17-6-12.
 */

public class HbaseReq implements Runnable{
    protected final static Logger logger = Logger.getLogger(HbaseReq.class);
    private static final String SPLIT_CHAR = "_";

    private OrgLog record;

    public HbaseReq(OrgLog record) {
        this.record = record;
    }

    @Override public void run() {
        String phone = record.getPhone();

        String phoneDigest = Identify.getPhonePinganDigest(phone);
        String startRowkey = Identify.DIGEST_TYPE_ADD_SALT_MD5 + SPLIT_CHAR + phoneDigest + SPLIT_CHAR + 10;
        String stopRowkey  = Identify.DIGEST_TYPE_ADD_SALT_MD5 + SPLIT_CHAR + phoneDigest + SPLIT_CHAR + 16;

        StopWatch stopwatch = new Log4JStopWatch("Hbase test");
        IHbaseConnection hbaseConnection = HbaseConnectionUtil.getInstance();
        String tableName = AppConfig.getInstance().getStringProperty(AppConfig.HBASE_TABLE_NAME);

        try {
            List<HbaseRow> rows = hbaseConnection.getResultScann(tableName, startRowkey, stopRowkey);
            logger.info(String.format("Table [ %s ], start Key [ %s ], return rows : %s ", tableName, startRowkey, rows.size()));
//            printRowMsg(rows);

            /*if(rows.size() > 0){
                List<ImsiMd5> imsiMd5 = Lists.newArrayList();

                for(HbaseRow row : rows){
                    for(HbaseValue value : row.getValues()){
                        String qualifier = Bytes.toString(value.getQualifier());
                        if(qualifier.equals("is_pingan")){
                            imsiMd5.add(new ImsiMd5().setImsi(Bytes.toString(value.getValue())));
                        }
                    }
                }

                Dao dao = DBconnFactory.getInstance().getDao();
                dao.fastInsert(imsiMd5);
            }*/
        } catch (Exception e) {
            logger.error("HbaseReq ", e);
        } finally {
            stopwatch.stop();
        }
    }

    private void printRowMsg(List<HbaseRow> rows) {
        for(HbaseRow row : rows){
            logger.info(row.getRowKey());
            for(HbaseValue value : row.getValues()){
                logger.info(Bytes.toString(value.getFamily()) + " :: " + Bytes.toString(value.getQualifier()) + " :: " + Bytes.toString(value.getValue()));
            }
        }
    }
}
