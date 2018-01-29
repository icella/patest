package ice.bean;

import java.util.Date;

public class QihuTagModel implements Comparable<QihuTagModel>{
	private String act;
	private Long time;
	private Date contactTime;
	public QihuTagModel(Long time,String act){
		this.time = time*1000;
		this.act = act;
		if(this.time !=null){
			contactTime= new Date(this.time);
		}
	}
	
	public QihuTagModel(){}
	
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Date getContactTime(){
		return contactTime;
	}
	public void setContactTime(Date contactTime) {
		this.contactTime = contactTime;
	}

	public int compareTo(QihuTagModel o) {
		if(this.time != null)
			return  (this.time.compareTo(o.getTime()));
		return this.contactTime.compareTo(o.getContactTime());
	}
}
