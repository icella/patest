package ice.bean.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * db_credit_site中通话详单
 * Created by lla on 17-7-3.
 */
@Table("tb_query_call_details")
public class QueryCallDetails {
    public QueryCallDetails() {
    }

    @Id
    @Column("cdid")
    private Integer cdid;

    @Column("lid")
    private Integer lid;

    @Column("call_time")
    private String call_time;

    @Column("duration")
    private Integer duration;

    @Column("call_model")
    private String call_model;

    @Column("format_call_model")
    private String formatCallModel;

    @Column("contact")
    private String contact;

    @Column("contact_type")
    private String called_type;

    @Column("call_addr")
    private String call_addr;
    
    @Column("contact_addr")
    private String contact_addr;

    @Column("called_type")
    private String calledType;

    @Column("cost")
    private double cost;

    @Column("create_time")
    private Date createTime;

    public Integer getCdid() {
        return cdid;
    }

    public QueryCallDetails setCdid(Integer cdid) {
        this.cdid = cdid;
        return this;
    }

    public Integer getLid() {
        return lid;
    }

    public QueryCallDetails setLid(Integer lid) {
        this.lid = lid;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public QueryCallDetails setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getFormatCallModel() {
        return formatCallModel;
    }

    public QueryCallDetails setFormatCallModel(String formatCallModel) {
        this.formatCallModel = formatCallModel;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public QueryCallDetails setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getCalledType() {
        return calledType;
    }

    public QueryCallDetails setCalledType(String calledType) {
        this.calledType = calledType;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public QueryCallDetails setCost(double cost) {
        this.cost = cost;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public QueryCallDetails setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCall_time() {
        return call_time;
    }

    public void setCall_time(String call_time) {
        this.call_time = call_time;
    }

    public String getCall_model() {
        return call_model;
    }

    public void setCall_model(String call_model) {
        this.call_model = call_model;
    }

    public String getCalled_type() {
        return called_type;
    }

    public void setCalled_type(String called_type) {
        this.called_type = called_type;
    }

    public String getCall_addr() {
        return call_addr;
    }

    public void setCall_addr(String call_addr) {
        this.call_addr = call_addr;
    }

    public String getContact_addr() {
        return contact_addr;
    }

    public void setContact_addr(String contact_addr) {
        this.contact_addr = contact_addr;
    }

    @Override public String toString() {
        try{
            return JSONObject.toJSONString(this);
        } catch (Exception e){
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
