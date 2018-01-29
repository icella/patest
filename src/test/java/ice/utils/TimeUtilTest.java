package ice.utils;

import junit.framework.TestCase;

/**
 * Created by lla on 17-4-10.
 */
public class TimeUtilTest extends TestCase {

    public void testRandomDateStr(){
        System.out.println(TimeUtil.getRandomDate("20151010", "20171010"));
    }
}
