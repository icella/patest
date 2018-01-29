//package ice;
//
//import com.google.common.collect.Lists;
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
//import ice.bean.ContactInfo;
//import ice.utils.SecureUtil;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import java.io.File;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by lla on 17-4-7.
// */
//public class ReadFileTest {
//    private final static Logger logger = Logger.getLogger(ReadFileTest.class);
//
//    private static final String Num86 = "86";
//    private static final String SPLIT_CHAR = "\t";
//
//    private static final String filePath = "";
//    private static final ExecutorService fileExecutor;
//    static {
//        fileExecutor = createExecutor("QihuTag-%d");
//    }
//
//    public static void main(String[] args) {
//        if (args.length != 1)
//            return;
//
//        String filePath = args[0];
//        exec(filePath);
////        exec("/home/lla/Documents/gray_phone_tag/test_phoneinfo_tag/input_files/");
//    }
//
//    private static void exec(String srcFile) {
//        ReadFileTest app = new ReadFileTest();
//        try {
//            List<ContactInfo> fileContents = app.buildInputFile(srcFile);
//            ExecutorService fileExecutor = createExecutor("QihuTag-%d");
////            Collections.shuffle(fileContents, new Random(System.currentTimeMillis()));
//
//            for (ContactInfo model : fileContents) {
//                fileExecutor.submit(new QihuTag(model, ""));
//            }
//
//            fileExecutor.shutdown();
//            fileExecutor.awaitTermination(5,TimeUnit.MINUTES);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static ExecutorService createExecutor(String tag) {
//        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
//            50,
//            100,
//            TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<Runnable>(),
//            new ThreadFactoryBuilder().setNameFormat(tag).setDaemon(true).build(),
//            new ThreadPoolExecutor.AbortPolicy());
//    }
//
//    private List<ContactInfo> buildInputFile(String inputFilePath) throws Exception {
//        List<File> allFiles = Lists.newArrayList(FileUtils.listFiles(new File(inputFilePath), null, false));
//
//        List<ContactInfo> fileList = Lists.newArrayList();
//        for (File file : allFiles) {
//            List<ContactInfo> tempList = Lists.newArrayList();
//            String fileName = file.getName();
//            List<String> lines = FileUtils.readLines(new File(inputFilePath + fileName));
//            for (String line : lines) {
//                ContactInfo record = getContactInfoRecord(line);
//                if(record != null){
//                    tempList.add(record);
//                }
//            }
//
//            fileList.addAll(tempList);
//        }
//
//        return fileList;
//    }
//
//    private ContactInfo getContactInfoRecord(String line) throws Exception {
//        String[] contents = line.split(SPLIT_CHAR, -1);
//        String phone = "";
//        String imsi = "";
//        String imei = "";
//        String phoneDigest = "";
//        String imsiDigest = "";
//        String imeiDigest = "";
//
//        if (contents.length < 1 ||StringUtils.isBlank(contents[0]))
//            return null;
//
//        try {
//            if (StringUtils.isNotEmpty(contents[0])) {
//                phone = contents[0];
//                phoneDigest = EnPhoneNumber(phone);
//            }
//
//            /*if (contents.length > 0 && StringUtils.isNotEmpty(contents[1])) {
//                imei = contents[1];
//                imeiDigest = fileIm(imei);
//            }
//
//            if (contents.length > 1 && StringUtils.isNotEmpty(contents[2])) {
//                imsi = contents[2];
//                imsiDigest = fileIm(imsi);
//            }*/
//        } catch (ArrayIndexOutOfBoundsException e) {
//            logger.error(StringUtils.substringAfterLast(e.getClass().getName(), "."));
//        }
//
//        ContactInfo contactInfo = new ContactInfo();
//        contactInfo.setPhone(phone)/*.setImsi(imsi).setImei(imei)*/.setPhoneDigest(phoneDigest)/*.setImsiDigest(imsiDigest)
//            .setImeiDigest(imeiDigest).setHasPhone(StringUtils.isNotBlank(phone))*/;
//
//        return contactInfo;
//    }
//
//    /**
//     * 固话不加86
//     */
//    private static String EnPhoneNumber(String phoneNumber){
//        if (StringUtils.isEmpty(phoneNumber))
//            return "";
//
//        if (phoneNumber.startsWith("0") || phoneNumber.startsWith(Num86)) {
//            return getDigest(phoneNumber);
//        }
//
//        return getDigest(Num86 + phoneNumber);
//    }
//
//    private static String getDigest(String param){
//        String digest = null;
//        try {
//            digest = StringUtils.isEmpty(param) ? "" : SecureUtil.md5("360pingan" + param).toLowerCase();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return digest;
//    }
//}
