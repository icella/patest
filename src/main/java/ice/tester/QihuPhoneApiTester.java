package ice.tester;

import com.google.common.collect.Lists;
import ice.AppConfig;
import ice.api.thirdpart.QihuAPIType;
import ice.api.thirdpart.QihuCommon;
import ice.api.thirdpart.QihuTag;
import ice.bean.ContactPair;
import ice.bean.dao.OrgLog;
import ice.utils.SecureUtil;
import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class QihuPhoneApiTester extends MainWorker {

    public QihuPhoneApiTester(Integer id, CountDownLatch latcher) {
        super(id, latcher);
    }

    @Override public void invoke(List<OrgLog> orgLogList) {
        List<ContactPair> contactList= Lists.newArrayList();
        for (OrgLog model : orgLogList) {
            ContactPair pairmodel = new ContactPair();

            String phone = model.getPhone();
            if(StringUtils.isNotEmpty(phone)){
                pairmodel.setPa(phone).setPad(EnPhoneNumber(phone));
            }

            String imsi = model.getMobileImsi();
            if(StringUtils.isNotEmpty(imsi)){
                pairmodel.setSa(imsi).setSad(getDigest(imsi));
            }

            String imei = model.getMobileImei();
            if(StringUtils.isNotEmpty(imei)){
                pairmodel.setEa(imei).setEad(getDigest(imei));
            }

            String contactStr = model.getBank();
            String[] contactSpliter = contactStr.split(",", -1);

            for (int i = 0; i < contactSpliter.length; i++) {
                String iValue = contactSpliter[i];
                if(StringUtils.isNotEmpty(iValue)){
                    String digestValue = getDigest(iValue);
                    if(i == 0 || i == 3){
                        pairmodel.setPb(iValue).setPbd(digestValue);
                    }

                    if(i == 1 || i == 3){
                        pairmodel.setSb(iValue).setSbd(digestValue);
                    }

                    if(i == 2 || i == 4){
                        pairmodel.setEb(iValue).setEbd(digestValue);
                    }
                }
            }

            contactList.add(pairmodel);
        }

        String type = AppConfig.getInstance().getStringProperty(AppConfig.QIHU_PHONE_REQUEST_TYPE);
        for (int t = 0; t< threadRepeatTimes; t++){
            for (int i = 0; i < contactList.size(); i++) {
                ContactPair record = contactList.get(i);
                if(type.equals(QihuAPIType.COMMON.getName())){
                    new QihuCommon(record, threadName, i).run();
                } else {
                    new QihuTag(record, threadName, i).run();
                }
            }
        }
    }

    @Override public SqlExpressionGroup subConditon() {
        SqlExpressionGroup group = Cnd.exps("sub_query_type", "=", "93001");

        String type = AppConfig.getInstance().getStringProperty(AppConfig.QIHU_PHONE_REQUEST_TYPE);
        if(type.equals(QihuAPIType.DIRECT.getName())){
            group = Cnd.exps("sub_query_type", "=", "93002");
        } else if(type.equals(QihuAPIType.COMMON.getName())){
            group = Cnd.exps("sub_query_type", "=", "93003");
        }

        return group;
    }

    @Override public void runMethod(OrgLog record) {

    }

    /**
     * 固话不加86
     */
    private static String EnPhoneNumber(String phoneNumber){
        final String Num86 = "86";
        if (StringUtils.isEmpty(phoneNumber))
            return "";

        if (phoneNumber.startsWith("0") || phoneNumber.startsWith(Num86)) {
            return getDigest(phoneNumber);
        }

        return getDigest(Num86 + phoneNumber);
    }

    private static String getDigest(String param){
        String digest = null;
        try {
            digest = StringUtils.isEmpty(param) ? "" : SecureUtil.md5("360pingan" + param).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digest;
    }
    }
