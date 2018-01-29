package ice.utils.pingan.identify;

import ice.utils.pingan.common.IdentifyFormatCheck;
import org.apache.commons.codec.digest.DigestUtils;

public final class Identify {
    private static final String PINGAN_DIGEST_KEY = "k3b^q]hd";
    private static final String QIHU_DIGEST_KEY = "360pingan";
    public static final String DIGEST_TYPE_ADD_SALT_MD5 = "15";

    public Identify() {
    }

    public static String getPhonePinganDigest(String phone_number) {
        return phone_number != null && IdentifyFormatCheck.isPhone(phone_number.trim())?DigestUtils.md5Hex("k3b^q]hd" + IdentifyFormatCheck.getPhoneNumber(phone_number)).toUpperCase():"";
    }

    public static String getPhone360Digest(String phone_number) {
        return phone_number != null && IdentifyFormatCheck.isPhone(phone_number.trim())?DigestUtils.md5Hex("360pingan86" + IdentifyFormatCheck.getPhoneNumber(phone_number)).toUpperCase():DigestUtils.md5Hex("360pingan86" + phone_number.trim()).toUpperCase();
    }

    public static String getImsiPinganDigest(String imsi) {
        return imsi != null && IdentifyFormatCheck.isImsi(imsi.trim())?DigestUtils.md5Hex("k3b^q]hd" + imsi.trim().toUpperCase()).toUpperCase():"";
    }

    public static String getImsi360Digest(String imsi) {
        return imsi != null && IdentifyFormatCheck.isImsi(imsi.trim())?DigestUtils.md5Hex("360pingan" + imsi.trim().toLowerCase()).toUpperCase():"";
    }

    public static String getImeiPinganDigest(String imei) {
        return imei != null && IdentifyFormatCheck.isImei(imei.trim())?DigestUtils.md5Hex("k3b^q]hd" + imei.trim().toUpperCase()).toUpperCase():"";
    }

    public static String getImei360Digest(String imei) {
        return imei != null && IdentifyFormatCheck.isImei(imei.trim())?DigestUtils.md5Hex("360pingan" + imei.trim().toLowerCase()).toUpperCase():"";
    }
}
