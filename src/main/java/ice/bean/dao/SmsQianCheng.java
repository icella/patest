package ice.bean.dao;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by lla on 17-9-22.
 */
@Table("sms_qiancheng")
public class SmsQianCheng {
    @Id
    @Column("seqid")
    private Integer seqid;

    @Column("sms_text")
    private String sms_text;

    public Integer getSeqid() {
        return seqid;
    }

    public void setSeqid(Integer seqid) {
        this.seqid = seqid;
    }

    public String getSms_text() {
        return sms_text;
    }

    public void setSms_text(String sms_text) {
        this.sms_text = sms_text;
    }
}
