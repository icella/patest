package ice.utils.hbase;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.NamespaceDescriptor.Builder;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class HbaseConnectionUtil implements IHbaseConnection {
    protected final static Logger logger = Logger.getLogger(HbaseConnectionUtil.class);
    private Configuration configuration = HBaseConfiguration.create();

    private Connection connection;
    private ExecutorService timeoutExecutors;

    private static class   HbaseNewConnectionHolder{
        private final static HbaseConnectionUtil instance = new HbaseConnectionUtil();
    }

    public static IHbaseConnection getInstance(){
        return HbaseConnectionUtil.HbaseNewConnectionHolder.instance;
    }

    private HbaseConnectionUtil() {
        configuration.set("hbase.zookeeper.quorum", HbaseCfg.ip);
        configuration.set("hbase.client.operation.timeout", HbaseCfg.operation_timeout);
        configuration.set("hbase.rpc.timeout", HbaseCfg.rcp_timeout);
        configuration.set("hbase.client.scanner.timeout.period", HbaseCfg.scanner_timeout_period);
        configuration.set("hbase.client.retries.number", HbaseCfg.retries_number);

        timeoutExecutors = new ThreadPoolExecutor(HbaseCfg.connection_max_thread_size,
            HbaseCfg.connection_max_thread_size,
            30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(HbaseCfg.connection_max_thread_size),
            new ThreadFactoryBuilder().setNameFormat("hbase-timeout-%d").build(),
            new ThreadPoolExecutor.DiscardPolicy());

        ExecutorService executor =
            new ThreadPoolExecutor(HbaseCfg.corePoolSize,
                HbaseCfg.connection_max_thread_size,
                30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(HbaseCfg.connection_max_thread_size),
                new ThreadFactoryBuilder().setNameFormat("hbase-connection-%d").build(),
                new ThreadPoolExecutor.DiscardPolicy());

        try {
            connection = ConnectionFactory.createConnection(configuration, executor);
//            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            logger.info("hbase io:", e);
        }
    }

    public HbaseConnectionUtil(String ip) throws IOException {
        configuration.set("hbase.zookeeper.quorum", ip);
        connection = ConnectionFactory.createConnection(configuration);
    }

    public HbaseConnectionUtil(String zookeeperQuorum, String ip) throws IOException {
        configuration.set(zookeeperQuorum, ip);
        connection = ConnectionFactory.createConnection(configuration);
    }

    public void addData(String rowKey, String tableName, String[] columnFamily, String[] values) throws Exception {
        TableName tName = TableName.valueOf(tableName);
        Table table = connection.getTable(tName);
        table.put(HbaseUtils.builderPuts(rowKey, columnFamily, values));
        table.close();
    }

    @Override public void addData(String rowKey, String tableName, String columnFamily, byte[] value) throws Exception {

    }


    public void createTable(String nameSpace, String tableName, String[] columnFamily) throws Exception {
        Admin admin = connection.getAdmin();

        TableName tName = TableName.valueOf(nameSpace, tableName);
        if (admin.tableExists(tName)) {
            throw new RuntimeException("table exists");
        }

        HTableDescriptor hTableDescriptor = new HTableDescriptor(tName);
        for (String familyName : columnFamily) {
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyName);
            hTableDescriptor.addFamily(columnDescriptor);
        }

        admin.createTable(hTableDescriptor);
        admin.close();
    }

    public HbaseRow getResult(final String tableName, final String rowKey) throws Exception {
        Future<HbaseRow> future = timeoutExecutors.submit(new Callable<HbaseRow>() {
            @Override public HbaseRow call() throws Exception {
                Table table = connection.getTable(TableName.valueOf(tableName));
                Get get = new Get(Bytes.toBytes(rowKey));

                try {
                    Result result = table.get(get);
                    HbaseRow row = HbaseUtils.getValue(result);
                    table.close();

                    return row;
                } finally {
                    closeTableQuietly(table);
                }
            }
        });

        try {
            return future.get(HbaseCfg.method_timeout, TimeUnit.SECONDS);
        } catch (Exception ie) {
            future.cancel(true);
            logger.info("getResult Error: ", ie);
        }

        return null;
    }

    @Override public List<HbaseRow> getBatchResult(String tableName, List<String> rowKeys) throws Exception {
        return null;
    }

    public List<HbaseRow> getResultScann(String tableName) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        ResultScanner resultScanner = null;

        List<HbaseRow> list;
        try {
            resultScanner = table.getScanner(new Scan());
            list = HbaseUtils.getValues(resultScanner);
        } finally {
            closeScannerQuietly(resultScanner);
            closeTableQuietly(table);
        }

        return list;
    }


    public List<HbaseRow> getResultScann(final String tableName, final String startRowkey, final String stopRowkey) throws
        Exception {
        /*List<HbaseRow> rs = Lists.newArrayList();

        Future<List<HbaseRow>> future = timeoutExecutors.submit(new Callable<List<HbaseRow>>() {
            @Override
            public List<HbaseRow> call() throws Exception {
                Table table = connection.getTable(TableName.valueOf(tableName));
                Scan scan = new Scan(Bytes.toBytes(startRowkey), Bytes.toBytes(stopRowkey));
                ResultScanner resultScanner = null;

                List<HbaseRow> list;
                try {
                    resultScanner = table.getScanner(scan);
                    list = HbaseUtils.getValues(resultScanner);
                } finally {
                    closeScannerQuietly(resultScanner);
                    closeTableQuietly(table);
                }

                return list;
            }
        });

        try {
            rs = future.get(HbaseCfg.method_timeout, TimeUnit.SECONDS);
        }
        catch (Exception ie) {
            future.cancel(true);
//            logger.info("getResultScann Error: ", ie);
            throw  new Exception(ie);
        }

        return rs;*/

        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan(Bytes.toBytes(startRowkey), Bytes.toBytes(stopRowkey));
        ResultScanner resultScanner = null;

        List<HbaseRow> list;
        try {
            resultScanner = table.getScanner(scan);
            list = HbaseUtils.getValues(resultScanner);
        } finally {
            closeScannerQuietly(resultScanner);
            closeTableQuietly(table);
        }

        return list;
    }

    public List<HbaseRow> getResultScann(final String tableName, final FilterList filterList) throws Exception {
        /*List<HbaseRow> rs = Lists.newArrayList();
        Future<List<HbaseRow>> future = timeoutExecutors.submit(new Callable<List<HbaseRow>>() {
            @Override
            public List<HbaseRow> call() throws Exception {
                Table table = connection.getTable(TableName.valueOf(tableName));
                Scan scan = new Scan();
                scan.setFilter(filterList);
                ResultScanner resultScanner = table.getScanner(scan);
                List<HbaseRow> list = HbaseUtils.getValues(resultScanner);
                resultScanner.close();
                table.close();
                return list;
            }
        });

        try {
            rs = future.get(HbaseCfg.method_timeout, TimeUnit.SECONDS);
        }
        catch (Exception ie) {
            future.cancel(true);
            logger.info("getResultScann Error: ", ie);
        }

        return rs;*/

        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.setFilter(filterList);
        ResultScanner resultScanner = table.getScanner(scan);
        List<HbaseRow> list = HbaseUtils.getValues(resultScanner);
        resultScanner.close();
        table.close();
        return list;
    }

    public void createNameSpace(String nameSpace) throws Exception {
        Admin admin = connection.getAdmin();
        Builder builder = NamespaceDescriptor.create(nameSpace);
        admin.createNamespace(builder.build());
        admin.close();
    }

    public void deleteNameSpace(String nameSpace) throws Exception {
        Admin admin = connection.getAdmin();
        admin.deleteNamespace(nameSpace);
        admin.close();
    }

    public void addDatas(String tableName, List<Put> puts) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        table.put(puts);
        table.close();
    }

    @Override public void close() throws Exception {
        connection.close();
    }

    @Override public void deleteData(String tableName, String rowKey) throws Exception {

    }

    @Override
    public List<HbaseRow> getResultScann(String tableName, String startRowkey, String stopRowkey, FilterList filterList)
        throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan(Bytes.toBytes(startRowkey), Bytes.toBytes(stopRowkey));
        scan.setFilter(filterList);
        ResultScanner resultScanner = table.getScanner(scan);
        List<HbaseRow> list = HbaseUtils.getValues(resultScanner);
        resultScanner.close();
        table.close();
        return list;
    }

    @Override public HbaseRow getResult(String tableName, String rowKey, FilterList filterList) throws Exception {
        return getResult(tableName, rowKey, null, null, filterList);
    }

    @Override public HbaseRow getResult(String tableName, String rowKey, String columnFamily, String[] qualifiers,
        FilterList filterList) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.setFilter(filterList);

        if (columnFamily != null) {
            for (String qualifier : qualifiers) {
                get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
            }
        }
        HbaseRow row = HbaseUtils.getValue(table.get(get));
        table.close();
        return row;
    }

    @Override public HbaseRow getResult(String tableName, String rowKey, String columnFamily) throws Exception {
        return null;
    }

    @Override public boolean isClose() {
        return false;
    }

    public Connection getConnection() {
        return connection;
    }

    private void closeTableQuietly(Table  table) throws IOException {
        if(table != null){
            try {
                table.close();
            } finally {

            }
        }
    }

    private void closeScannerQuietly(ResultScanner resultScanner){
        if(resultScanner != null){
            try {
                resultScanner.close();
            } finally {

            }
        }
    }
}

