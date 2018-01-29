package ice.utils.pingan.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentifyFormatCheck {
    private static final Pattern imei_pattern = Pattern.compile("^[0-9a-fA-F]{14,15}$");
    private static final Pattern imsi_pattern = Pattern.compile("^460\\d{11,12}$");
    private static final Pattern phone_pattern = Pattern.compile("^((\\+86)|(86))?1\\d{10}$");
    private static final Pattern mac_pattern = Pattern.compile("^([0-9A-Fa-f]{1,2}[-]){5}([0-9A-Fa-f]{1,2})$|^([0-9A-Fa-f]{1,2}[:]){5}([0-9A-Fa-f]{1,2})$|^([0-9A-Fa-f]{1,2}[.]){5}([0-9A-Fa-f]{1,2})$");

    public IdentifyFormatCheck() {
    }

    public static boolean isImei(String imeiStr) {
        if(imeiStr != null && imeiStr.trim().length() != 0) {
            Matcher matcher = imei_pattern.matcher(imeiStr);
            if(matcher.matches()) {
                String partImei = imeiStr.toLowerCase().replaceAll("[a-fA-f]", "");
                if(partImei.trim().length() > 0 && partImei.replaceAll(partImei.substring(0, 1), "").trim().length() > 1) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isImsi(String imsiStr) {
        if(imsiStr != null && imsiStr.trim().length() != 0) {
            Matcher matcher = imsi_pattern.matcher(imsiStr);
            if(matcher.matches()) {
                String partImsi = imsiStr.substring(3);
                if(partImsi.replaceAll(partImsi.substring(0, 1), "").trim().length() > 1) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isPhone(String phoneStr) {
        if(phoneStr != null && phoneStr.trim().length() != 0) {
            Matcher matcher = phone_pattern.matcher(phoneStr);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static boolean isMac(String macStr) {
        if(macStr != null && macStr.trim().length() != 0) {
            Matcher matcher = mac_pattern.matcher(macStr);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static String getPhoneNumber(String phone) {
        if(phone != null && phone.trim().length() >= 11) {
            phone = phone.trim();
            return phone.substring(phone.length() - 11);
        } else {
            return "";
        }
    }
}
