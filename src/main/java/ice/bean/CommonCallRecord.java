package ice.bean;

import java.util.Date;

public class CommonCallRecord implements Comparable<CommonCallRecord>{
	public static final Integer PHONE2IMEI = 0; // 号码打给设备
	public static final Integer IMEI2PHONE = 1; // 设备打给号码

	private Date callTime;
	private Long time;
	private Integer tag;
	private String tagName;

	public CommonCallRecord(Long time, Integer tag) {
		this.time = time * 1000;
		this.tag = tag;
		if (this.time != null) {
			callTime = new Date(this.time);
		}
		
		if(this.tag != null){
			tagName = (tag == PHONE2IMEI ? "主叫" : "被叫");
		}
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public int compareTo(CommonCallRecord o) {
		if(this.time != null)
			return this.time.compareTo(o.getTime());
		
		return this.callTime.compareTo(o.getCallTime());
	}
}
