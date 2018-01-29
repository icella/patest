package ice.bean.dao;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by lla on 17-6-12.
 */
@Table("tb_pa_imsi")
public class ImsiMd5 {
    @Id @Column("id") private int id;

    @Column("imsi") private String imsi;

    public int getId() {
        return id;
    }

    public ImsiMd5 setId(int id) {
        this.id = id;
        return this;
    }

    public String getImsi() {
        return imsi;
    }

    public ImsiMd5 setImsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    @Override public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
