package ice.utils.hbase;

import java.util.List;

public class HbaseRow {
	private String rowKey;
	private List<HbaseValue> values;

	
	
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public List<HbaseValue> getValues() {
		return values;
	}
	public void setValues(List<HbaseValue> values) {
		this.values = values;
	}	
}
