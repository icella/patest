package ice.bean.dao;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Created by lla on 17-9-22.
 */
@Table("sms_log")
public class SmsLog {
    @Id
    @Column("seqid")
    private long seqid;

    @Column("trade_no")
    private String trade_no;

    @Column("inner_no")
    private String inner_no;

    @Column("vkey")
    private String vkey;

    @Column("taskstatus")
    private Integer taskstatus;

    @Column("smscount")
    private Integer smscount;

    @Column("sms_text")
    private String sms_text;

    @Column("result_text")
    private String result_text;

    @Column("result_code")
    private Integer result_code;

    @Column("createtime")
    private Date createtime;

    @Column("completetime")
    private Date completetime;

    @Column("receivetime")
    private Date receivetime;

    public long getSeqid() {
        return seqid;
    }

    public void setSeqid(long seqid) {
        this.seqid = seqid;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getInner_no() {
        return inner_no;
    }

    public void setInner_no(String inner_no) {
        this.inner_no = inner_no;
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public Integer getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(Integer taskstatus) {
        this.taskstatus = taskstatus;
    }

    public Integer getSmscount() {
        return smscount;
    }

    public void setSmscount(Integer smscount) {
        this.smscount = smscount;
    }

    public String getSms_text() {
        return sms_text;
    }

    public void setSms_text(String sms_text) {
        this.sms_text = sms_text;
    }

    public String getResult_text() {
        return result_text;
    }

    public void setResult_text(String result_text) {
        this.result_text = result_text;
    }

    public Integer getResult_code() {
        return result_code;
    }

    public void setResult_code(Integer result_code) {
        this.result_code = result_code;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    public Date getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }
}
