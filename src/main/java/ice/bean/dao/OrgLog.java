package ice.bean.dao;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

// 机构查询接口
@Table("tb_org_log${cid}") public class OrgLog {
    public OrgLog() {
    }

    @Id
    @Column("query_id")
    private Integer queryId;
    @Column("app_key") private String appKey;
    @Column("app_ip") private String appIp;
    @Column("bank") private String bank;
    @Column("card") private String card;
    @Column("phone") private String phone;
    @Column("query_type") private Integer queryType;
    @Column("name") private String name;
    @Column("area_code") private String areaCode;
    @Column("id_card") private String idCard;
    @Column("query_time") private Date queryTime;
    @Column("op_result") private Integer opResult;
    @Column("result_count") private Integer resultCount;
    @Column("create_time") private Date createTime;
    @Column("org_name") private String orgName;
    @Column("mobile_imsi") private String mobileImsi;
    @Column("mobile_imei") private String mobileImei;
    @Column("query_source") private Integer querySource;
    @Column("match_types") private String matchTypes;
    @Column("user_name") private String userName;
    @Column("query_mode") private Integer queryMode;
    @Column("youdun_imsi_used") private Integer youdunImsiUsed;
    @Column("sub_query_type") private Integer subQueryType;
    @Column("error_type") private Integer errorType;
    @Column("trade_no") private String tradeNo;
    @Column("uid")
    private String uid;

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }

    public Integer getOpResult() {
        return opResult;
    }

    public void setOpResult(Integer opResult) {
        this.opResult = opResult;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMobileImsi() {
        return mobileImsi;
    }

    public void setMobileImsi(String mobileImsi) {
        this.mobileImsi = mobileImsi;
    }

    public String getMobileImei() {
        return mobileImei;
    }

    public void setMobileImei(String mobileImei) {
        this.mobileImei = mobileImei;
    }

    public Integer getQuerySource() {
        return querySource;
    }

    public void setQuerySource(Integer querySource) {
        this.querySource = querySource;
    }

    public String getMatchTypes() {
        return matchTypes;
    }

    public void setMatchTypes(String matchTypes) {
        this.matchTypes = matchTypes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(Integer queryMode) {
        this.queryMode = queryMode;
    }

    public Integer getYoudunImsiUsed() {
        return youdunImsiUsed;
    }

    public void setYoudunImsiUsed(Integer youdunImsiUsed) {
        this.youdunImsiUsed = youdunImsiUsed;
    }

    public Integer getSubQueryType() {
        return subQueryType;
    }

    public void setSubQueryType(Integer subQueryType) {
        this.subQueryType = subQueryType;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getAppIp() {
        return appIp;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
