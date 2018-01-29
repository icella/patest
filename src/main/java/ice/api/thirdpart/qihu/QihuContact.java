package ice.api.thirdpart.qihu;

import ice.utils.HttpClientUtil;
import ice.utils.SecureUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by lla on 17-4-24.
 */
public abstract class QihuContact implements Callable{
    protected final static Logger logger = Logger.getLogger(QihuContact.class);
    private final static String token = "360test";
    abstract String prepareEncryptionData();

    protected synchronized String getHqContent(String text, String url) throws Exception {
        String resp;
        byte[] encData;
        long start = System.currentTimeMillis();
        JNAQIHU jnaqihu = new JNAQIHU();
        try {
            encData = jnaqihu.encryption(text);
        } catch (Exception e) {
            throw new Exception("加密出错", e);
        }

        byte[] respData;
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(buildHttpEntity(encData));
            respData = HttpClientUtil.getInstance().post(post);
        } catch (Exception e1) {
            throw new Exception("加密后发送请求到360服务器出错！", e1);
        }

        if (respData == null) {
            throw new Exception(
                "加密后发送请求到360服务器，返回结果为null.(加密->请求->返回)耗时： " + (System.currentTimeMillis() - start) + "ms");
        }

        try {
            resp = new String(jnaqihu.decryption(respData));
        } catch (Exception e) {
            throw new Exception("解密出错!本次请求360服务器的过程(加密->请求->返回->解密)耗时： " + (System.currentTimeMillis() - start) + "ms",
                e);
        }

        return resp;
    }

    protected HttpEntity buildHttpEntity(byte[] encData) throws Exception {
        Long timestamp = new Date().getTime() / 1000;

        byte tk[] = token.getBytes();
        byte ts[] = timestamp.toString().getBytes();
        int len = encData.length + tk.length + ts.length;
        byte md5byte[] = new byte[len];

        System.arraycopy(tk, 0, md5byte, 0, tk.length);
        System.arraycopy(ts, 0, md5byte, tk.length, ts.length);
        System.arraycopy(encData, 0, md5byte, tk.length + ts.length, encData.length);
        String vk = SecureUtil.md5(md5byte).substring(0, 8);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("data", encData);
        builder.addTextBody("ct", timestamp.toString());
        builder.addTextBody("vk", vk);

        return builder.build();
    }
}
