package ice.utils.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Purpose:
 * 
 * @see	    
 * @since   1.1.0
 */
public final class HbaseUtils
{

	public static Put builderPuts(String rowKey, String[] columnFamily, String[] values) {

		Put put = new Put(Bytes.toBytes(rowKey));
		int i = 0;
		for (String c : columnFamily) {
			String family = c.split(":")[0];
			String qualifier = c.split(":")[1];
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(values[i]));
			i++;
		}
		return put;
	}
    
     public static  TableName builderTableName(String tableName){
         return TableName.valueOf(tableName);
     }
     
     public static  HbaseRow getValue(Result result)throws Exception {
    	 if (result.isEmpty())
    		 return null;
    	 HbaseRow hbaseRow = new HbaseRow();
    	 List<HbaseValue> values = new ArrayList<HbaseValue>();
         for (Cell cell : result.listCells()) {
        	values.add(new HbaseValue(CellUtil.cloneRow(cell),
        							CellUtil.cloneFamily(cell),
        							CellUtil.cloneQualifier(cell),
        							CellUtil.cloneValue(cell),
        							cell.getTimestamp()));
         }
         hbaseRow.setRowKey(Bytes.toString(result.getRow()));
         hbaseRow.setValues(values);
         return hbaseRow;
     }
     
     public static List<HbaseRow> getValues(ResultScanner resultScanner)throws Exception {
    	 List<HbaseRow> list = new ArrayList<HbaseRow>();
    	 for (Result result : resultScanner) {
			list.add(getValue(result));
		 }
    	return list;
     }
}

