package ice.api.thirdpart.qihu;

import com.alibaba.fastjson.JSON;
import ice.AppConfig;
import ice.bean.ContactTagRs;

import java.util.ArrayList;
import java.util.List;

public class ContacTag extends QihuContact {
    private String pa;
    private String sa;
    private String ea;
    private String pb;
    private String sb;
    private String eb;

    public ContacTag(String pa, String sa, String ea, String pb, String sb, String eb) throws Exception {
        this.pa = pa;
        this.sa = sa;
        this.ea = ea;
        this.pb = pb;
        this.sb = sb;
        this.eb = eb;
    }

    public ContactTagRs call(){
        ContactTagRs result = new ContactTagRs();
        String text = prepareEncryptionData();
        String errResp = "";
        try {
            String resp = getHqContent(text, AppConfig.getInstance().getStringProperty(AppConfig.QIHU_PHONE_TAG));
            errResp = resp;
            result = JSON.parseObject(resp, ContactTagRs.class);
            result.initData();
            result.setJsonData(resp);
        } catch (Exception e) {
            logger.error("查询直接通话和活跃度信息时，请求360接口出现异常." + errResp, e);
        }

        return result;
    }


    @Override String prepareEncryptionData() {
        List<String[]> datas = new ArrayList<>();
        String[] dataA = new String[] {pa, ea, sa};
        String[] dataB = new String[] {pb, eb, sb};
        datas.add(dataA);
        datas.add(dataB);

        return JSON.toJSONString(datas);
    }
}
