package ice.utils.pingan.identify;

public enum IdentifyType {
    IMSI("10", "SIM卡唯一编码"),
    IMEI("15", "手机设备唯一编码"),
    PHONE("20", "手机号"),
    DATA("25", "数据");

    private String identifyId;
    private String identifyDesc;

    IdentifyType(String identifyId, String identifyDesc) {
        this.identifyId = identifyId;
        this.identifyDesc = identifyDesc;
    }

    public String getIdentifyId() {
        return this.identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    public String getIdentifyDesc() {
        return this.identifyDesc;
    }

    public void setIdentifyDesc(String identifyDesc) {
        this.identifyDesc = identifyDesc;
    }
}
