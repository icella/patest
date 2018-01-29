package ice;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lla on 17-7-25.
 */
public class MultRequestStat {
    private static final Logger logger = Logger.getLogger(MultRequestStat.class);

    public static void main(String[] args) {
        int clients = Integer.parseInt(args[0]);
        int timeWidth = Integer.parseInt(args[1]);

        int totalTps = 0;
        int flag = 0;
        int totalPerTime = 0;

        final String url = args[2];
        final long start = System.currentTimeMillis();

        while(System.currentTimeMillis() - start < timeWidth){
            final AtomicInteger atomic = new AtomicInteger();
            final AtomicInteger atomicTp = new AtomicInteger();
            final long[] times = new long[clients];

            for (int i = 0; i < clients; i++) {
                new Thread(){
                    @Override public void run() {
                        try{
                            long tpStart = System.currentTimeMillis();
                            HttpClient httpClient = new HttpClient();
                            GetMethod method = new GetMethod(url);
                            method.setRequestHeader("Connection", "close");
                            int statusCode;

                            try{
                                statusCode = httpClient.executeMethod(method);
                                if(statusCode != HttpStatus.SC_OK){
                                    logger.error("Method failed:" + method.getStatusLine());
                                }

                                method.releaseConnection();
                                times[atomicTp.incrementAndGet() - 1] = System.currentTimeMillis() - tpStart;
                            } catch (HttpException e){
                                logger.error(e);
                            } catch (IOException e){
                                logger.error(e);
                            }
                        } finally {
                            atomic.incrementAndGet();
                        }
                    }
                }.start();
            }

            while (atomic.get() < clients){
            }

            long total = 0;
            for (int i = 0; i < times.length; i++) {
                total += times[i];
            }

            long avgPerTime = total /clients;
            long thisTps = (1000 /avgPerTime) * clients;
            flag++;
            totalTps  += thisTps;
            totalPerTime += avgPerTime;
        }

        long allAvgTps = totalTps / flag;
        long allPerTime = totalPerTime /flag;
        logger.info("all avg tps is : " + allAvgTps);
        logger.info("all avg per time is : " +  allPerTime);
    }
}
