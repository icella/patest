package ice.utils.hbase;

public class HbaseValue {
	
	private byte [] rowKey;
	private byte [] family;
	private byte [] qualifier;
	private byte [] value;
	private Long timestamp;
	
	public HbaseValue(){}
	
	
	
	public HbaseValue(byte[] rowKey, byte[] family, byte[] qualifier, byte[] value,
			Long timestamp) {
		super();
		this.rowKey = rowKey;
		this.family = family;
		this.qualifier = qualifier;
		this.value = value;
		this.timestamp = timestamp;
	}

	public byte[] getRowKey() {
		return rowKey;
	}
	public void setRowKey(byte[] rowKey) {
		this.rowKey = rowKey;
	}
	public byte[] getFamily() {
		return family;
	}
	public void setFamily(byte[] family) {
		this.family = family;
	}
	public byte[] getQualifier() {
		return qualifier;
	}
	public void setQualifier(byte[] qualifier) {
		this.qualifier = qualifier;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
