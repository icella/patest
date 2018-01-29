package ice.api.thirdpart.qihu;

import com.alibaba.fastjson.JSON;
import ice.AppConfig;
import ice.bean.ContactCommonRs;

import java.util.ArrayList;
import java.util.List;

public class ContactCommon extends QihuContact{
    private String pa;
    private String pb;

    private String pad;
    private String pbd;

    public ContactCommon(String pa, String pb, String pad, String pbd) throws Exception {
        this.pa = pa;
        this.pb = pb;
        this.pad = pad;
        this.pbd = pbd;
    }

    @Override String prepareEncryptionData() {
        List<String> datas = new ArrayList<>();
        datas.add(pad);
        datas.add(pbd);

        return JSON.toJSONString(datas);
    }

    public ContactCommonRs call(){
        String text = prepareEncryptionData();
        ContactCommonRs result = new ContactCommonRs();
        try {
            String resp = getHqContent(text, AppConfig.getInstance().getStringProperty(AppConfig.QIHU_PHONE_COMMON));
            result = JSON.parseObject(resp, ContactCommonRs.class);
            result.initData(pa, pb);
            result.setJsonData(resp);
        } catch (Exception e) {
            logger.error("CommonContacts request error !", e);
        }

        return result;
    }
}
