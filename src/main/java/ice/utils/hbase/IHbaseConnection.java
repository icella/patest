
package ice.utils.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.filter.FilterList;

import java.util.List;

public interface IHbaseConnection
{
    
    void createNameSpace(String nameSpace)throws Exception;
    
    void deleteNameSpace(String nameSpace)throws Exception;

    void createTable(String nameSpace, String tableName, String[] columnFamily)throws Exception;

    void addData(String rowKey, String tableName, String[] columnFamily, String[] values)throws Exception;
    

    void addData(String rowKey, String tableName, String columnFamily, byte[] value) throws Exception;
    

    void addDatas(String tableName, List<Put> puts) throws Exception;

    HbaseRow  getResult(String tableName, String rowKey) throws Exception;
    

    List<HbaseRow> getBatchResult(String tableName, List<String> rowKeys) throws Exception;

    List<HbaseRow> getResultScann(String tableName)throws Exception;

    List<HbaseRow> getResultScann(String tableName, String startRowkey, String stopRowkey) throws Exception;
    

    List<HbaseRow> getResultScann(String tableName, String startRowkey, String stopRowkey, FilterList filterList) throws
        Exception;

    List<HbaseRow> getResultScann(String tableName, FilterList filterList)throws Exception;

    HbaseRow getResult(String tableName, String rowKey, FilterList filterList)throws Exception;
    

    HbaseRow getResult(String tableName, String rowKey, String columnFamily, String[] qualifiers, FilterList filterList)throws
        Exception;
    

    HbaseRow getResult(String tableName, String rowKey, String columnFamily) throws Exception;
    
    boolean isClose();
    
    void close()throws Exception;
    
    void deleteData(String tableName, String rowKey) throws Exception;
}


/**
 * $Log: IHbaseConnection.java,v $
 * 
 * @version 1.0 2015-7-10 
 *
 */
