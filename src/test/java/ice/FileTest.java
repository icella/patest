package ice;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by lla on 17-8-25.
 */
public class FileTest {
    private static final  Logger logger= Logger.getLogger(FileTest.class);

    public static void main(String[] args) {
        List<String>  fileContent = new FileTest().getRecordsFromFile();
        List<List<String>> lists = Lists.partition(fileContent, 1000);
        logger.info(lists.size());
    }

    private List<String> getRecordsFromFile() {
        try {
            return FileUtils
                .readLines(new File(getClass().getClassLoader().getResource("yellowpage_10000.txt").getFile()));
        } catch (IOException e) {
            logger.error(e);
        }

        return null;
    }
}
