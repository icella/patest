package ice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/*
 * 正则表达式校验工具类
 */
public class RegExpValidateUtil
{
	private static final Pattern TEL_REG_EXP     = Pattern.compile("((^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))|(^\\d{5,10}$)");  
//	private static final Pattern TEL_REG_EXP     = Pattern.compile("(^\\d{5,12}$)");
//	private static final Pattern TEL_REG_EXP     = Pattern.compile("((^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))");  
	private static final Pattern MOBILE_REG_EXP  = Pattern.compile("^1\\d{10}$");  
	private static final Pattern IMSI_REG_EXP    = Pattern.compile("^460\\d{11,12}$");
	private static final Pattern IMEI_REG_EXP    = Pattern.compile("^[0-9a-fA-F]{14,15}$");
	
	public static final boolean isTelephone(String telephone)
	{
		return regExpMatch(TEL_REG_EXP, telephone);
	}
	
	public static final boolean isMobile(String mobile)
	{
		return regExpMatch(MOBILE_REG_EXP, mobile);
	}
	
	public static final boolean isImsi(String imsi)
	{
		
		return regExpMatch(IMSI_REG_EXP, imsi);
	}
	
	public static final boolean isImei(String imei)
	{
		
		return regExpMatch(IMEI_REG_EXP, imei);
	}
	
	private static final boolean regExpMatch(Pattern pattern, String matchStr)
	{
		if(StringUtils.isBlank(matchStr))
		{
			return false;
		}
		Matcher matcher = pattern.matcher(matchStr);
      return matcher.matches();
  }
	
	public static void main(String[] args)
	{
		System.out.println("Is mobile:" + isMobile("15907476665"));
//		System.out.println("Is imsi:" + isImsi("46003030280862"));
	}
}
