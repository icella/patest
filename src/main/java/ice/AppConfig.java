package ice;

import ice.utils.ParamPreConditions;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lla on 17-4-10.
 */
public class AppConfig {
    private final static Logger logger = Logger.getLogger(AppConfig.class);

    public final static String API_NAMES = "api_names";
    public final static String V_USER = "v_user";
    public final static String TOTAL_TIMEOUT = "total_timeout";
    public final static String DB_PAGE_START = "db_page_start";
    public final static String DB_PAGE_LIMIT = "db_page_limit";
    public final static String DB_BEGIN_DATE = "db_beginDate";
    public final static String DB_END_DATE = "db_endDate";
    public final static String HTTP_SOCKET_TIMEOUT = "http_socket_timeout";
    public final static String HTTP_CONNECT_TIMEOUT = "http_connect_timeout";
    public final static String HTTP_CONNECT_REQUEST_TIMEOUT = "http_connect_request_timeout";
    public final static String THREAD_REPEAT_TIMES = "thread_repeat_times";
    public static final String PRINT_INPUT_PRMS = "print_input_prms";

    public final static String  UPC_PNAME = "upc_pname";
    public final static String  UPC_VKEY = "upc_vkey";
    public final static String  UPC_URL = "upc_url";

    public final static String  PHONEINFO_FAST_URL = "phoneinfo_fast_url";
    public final static String  PHONEINFO_INFOTYPES = "phoneinfo_infotypes";
    public final static String  PHONEINFO_ASYNC = "phoneinfo_async";
    public final static String  PHONEINFO_URL = "phoneinfo_url";

    public final static String  BLACKLIST_URL = "blacklist_url";
    public final static String  OVERDUECLASSIFY_URL = "overdueClassify_url";
    public final static String  LOANCLASSIFY_URL = "loanClassify_url";
    public final static String  MODELSCORE_URL = "modelscore_url";
    public final static String  PHKJMODELERSCORE_URL = "phkjModelerScore_url";
    public final static String  LOGSTAT_URL = "logStat_url";
    public static final String  PHONETAG_URL = "phonetag_url";
    public static final String  CALLDETAILANALYSISREPORT_URL = "callDetailAnalysisReport_url";
    public static final String  TESTINTERCEPTOR_URL = "testInterceptor_url";
    public static final String  VARIABLEAPi_URL = "variable_url";
    public static final String  VARIABLEA_VERSION = "variable_version";
    public static final String  NULLRESULT_URL = "nullResult_url";
    public static final String  YELLOWPAGE_URL = "yellowPage_url";
    public static final String  DUN_NUMBER_MARK_URL = "dun_number_mark_url";
    public static final String  UPLOAD_TEXT_URL = "upload_text_url";
    public static final String  STRUCTURED_RESULT_URL = "structured_result_url";

    public final static String QIHU_PHONE_TAG = "qihu_phone_tag";
    public final static String QIHU_PHONE_COMMON = "qihu_phone_common";
    public final static String QIHU_PHONE_REQUEST_TYPE = "qihu_phone_request_type";

    public static final String HBASE_TABLE_NAME = "hbase_table_name";
    public static final String USE_UID = "use_uid";
    public static final String ORGLOG_SUFFIX = "orglog_suffix";
    public static final String CALL_DETAIL_ANALYSIS_REPORT_PDF_PATH = "call_detail_analysis_report_pdf_path";
    public static final String USE_MD5 = "use_md5";

    private Properties properties;

    public static class AppConfigHolder {
        private static AppConfig instance = new AppConfig();
    }

    public static AppConfig getInstance() {
        return AppConfigHolder.instance;
    }

    private AppConfig() {
        init();
    }

    private void init() {
        /*String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        logger.info(path);
        String externalPath = new File(path).getPath() + "/config.properties";*/

        String externalPath = System.getProperty("user.dir") + "/config/config.properties";
        PropertyConfigurator.configure(externalPath);
        logger.info(externalPath);

        properties = new Properties();

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(externalPath));
            properties.load(in);

            for (Map.Entry entry: properties.entrySet()) {
                logger.info(entry.getKey() + " :: " + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            logger.error("文件没有找到!" + externalPath, e);
        } catch (IOException e) {
            logger.error("文件读取失败!" + externalPath, e);
        }

    }

    public Properties getProperties(String fileName) {
        String externalPath = System.getProperty("user.dir") + "/config/" + fileName;
        try {
            /*String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            String externalPath = new File(path).getPath() + "/" + fileName;*/
            PropertyConfigurator.configure(externalPath);

            InputStream in = new BufferedInputStream(new FileInputStream(externalPath));

            Properties p = new Properties();
            p.load(in);

            return p;
        } catch (FileNotFoundException e) {
            logger.error("文件没有找到!" + externalPath, e);
        } catch (IOException e) {
            logger.error("文件读取失败!" + externalPath, e);
        }
        return null;
    }

    private Properties getConfigProperties() {
        return getProperties("config.properties");
    }

    public String getProperty(String name){
        Object object = properties.get(name);
        ParamPreConditions.checkNotNull(object, "[ " + name + " ] not exist!");

        return (String) object;
    }

    public int getIntProperty(String name){
        return Integer.parseInt(getProperty(name));
    }

    public boolean getBooleanProperty(String name){
        return Boolean.parseBoolean(getProperty(name));
    }

    /**
     * same to {@link AppConfig#getConfigProperties()}
     * @param name
     * @return
     */
    public String getStringProperty(String name){
        return getProperty(name);
    }

    public long getLongProperty(String name){
        return Long.parseLong(getProperty(name));
    }

    public List<String> getListProperty(String name){
        String value = getProperty(name);
//        Preconditions.checkArgument(value.contains(","), "不包含逗号(,),请检查config.properties中参数" + name);
        String[] splitter = value.split(",", -1);
        ParamPreConditions.checkArgument(splitter.length != 0, "参数未配置,请检查config.properties中参数" + name);

        return Arrays.asList(splitter);
    }
}
