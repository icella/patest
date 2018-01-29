package ice.utils;

import ice.AppConfig;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientUtil {
  private final static Logger log = Logger.getLogger(HttpClientUtil.class);

  private PoolingHttpClientConnectionManager httpClientManager;
  private CloseableHttpClient httpClient;

  public CloseableHttpClient getHttpClient() {
    return this.httpClient;
  }

  private static class LazyHolder {
    private static final HttpClientUtil _instance = new HttpClientUtil();
  }

  public static final HttpClientUtil getInstance() {
    return LazyHolder._instance;
  }

  private HttpClientUtil() {
    try {
      init();
    } catch (Exception e) {
      log.error("httpclient init error", e);
    }
  }

  private void init() throws Exception {
    ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
    SSLContext sslcontext = SSLContext.getInstance("TLS");
    sslcontext.init(null, new TrustManager[] {trustAllManager}, null);

    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new HostnameVerifier() {
      @Override public boolean verify(String s, SSLSession sslSession) {
        return true;
      }
    });
    Registry registry =
        RegistryBuilder.create().register("http", plainsf).register("https", sslsf).build();

    this.httpClientManager = new PoolingHttpClientConnectionManager(registry);
    this.httpClientManager.setMaxTotal(1000);
    this.httpClientManager.setDefaultMaxPerRoute(20);

    HttpHost localhost = new HttpHost("w-sweb20.safe.zzbc.qihoo.net");
    this.httpClientManager.setMaxPerRoute(new HttpRoute(localhost), 500);
    this.httpClient = HttpClients.custom().setConnectionManager(this.httpClientManager).build();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new ConnectionMonitorThread(this.httpClientManager));
  }

  public static void config(HttpRequestBase httpRequestBase) {
    AppConfig config = AppConfig.getInstance();

    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(config.getProperty(AppConfig.HTTP_CONNECT_REQUEST_TIMEOUT)))
        .setConnectTimeout(Integer.parseInt(config.getProperty(AppConfig.HTTP_CONNECT_TIMEOUT)))
        .setSocketTimeout(Integer.parseInt( config.getProperty(AppConfig.HTTP_SOCKET_TIMEOUT)))
        .build();
    httpRequestBase.setConfig(requestConfig);
  }

  public String postStr(HttpPost post) throws Exception {
    CloseableHttpResponse response = null;
    try {
      CloseableHttpClient httpclient = getHttpClient();
      HttpClientUtil.config(post);

      response = httpclient.execute(post, HttpClientContext.create());
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != 200) {
        EntityUtils.consume(response.getEntity());

        return String.valueOf(statusCode);
      }

      HttpEntity responseEntity = response.getEntity();
      if ((response.containsHeader("Content-Encoding"))
          && ("gzip".equalsIgnoreCase(response.getFirstHeader("Content-Encoding").getValue()))) {
        responseEntity = new GzipDecompressingEntity(responseEntity);
      }

      int status = response.getStatusLine().getStatusCode();
      if (status >= 200 && status < 300) {
        HttpEntity entity = response.getEntity();
        return entity != null ? EntityUtils.toString(entity) : null;
      } else {
        throw new ClientProtocolException("Unexpected response status: " + status);
      }

    }  catch (SocketTimeoutException se) {
      se.printStackTrace();
      throw new SocketTimeoutException("HttpClientUtil::post::exception occurs!");
    } catch (ConnectTimeoutException ce) {
      ce.printStackTrace();
      throw new ConnectTimeoutException("HttpClientUtil::post::exception occurs!");
    } catch (Exception e) {
      throw new Exception("HttpClientUtil::post::exception occurs!", e);
    }
    finally {
      post.abort();
      if (response != null) {
        try {
          EntityUtils.consume(response.getEntity());
          response.close();
        } catch (Exception e2) {
        }
      }
    }
  }

  public byte[] post(HttpPost post) throws Exception {
    CloseableHttpResponse response = null;
    try {
      CloseableHttpClient httpclient = getHttpClient();
      HttpClientUtil.config(post);

      response = httpclient.execute(post, HttpClientContext.create());
      if (response.getStatusLine().getStatusCode() != 200) {
        EntityUtils.consume(response.getEntity());

        return null;
      }

      HttpEntity responseEntity = response.getEntity();
      if ((response.containsHeader("Content-Encoding"))
          && ("gzip".equalsIgnoreCase(response.getFirstHeader("Content-Encoding").getValue()))) {
        responseEntity = new GzipDecompressingEntity(responseEntity);
      }

      byte[] respData = EntityUtils.toByteArray(responseEntity);

      return respData;
    } catch (SocketTimeoutException se) {
      se.printStackTrace();
      throw new SocketTimeoutException("HttpClientUtil::post::exception occurs!");
    } catch (ConnectTimeoutException ce) {
      ce.printStackTrace();
      throw new ConnectTimeoutException("HttpClientUtil::post::exception occurs!");
    } catch (Exception e) {
      throw new Exception("HttpClientUtil::post::exception occurs!", e);
    } finally {
      post.abort();
      if (response != null) {
        try {
          EntityUtils.consume(response.getEntity());
          response.close();
        } catch (Exception e2) {
        }
      }
    }
  }

  public String get(String url, Header[] headers) throws Exception {
    CloseableHttpResponse response = null;
    try {
      CloseableHttpClient httpclient = getHttpClient();
      HttpGet get = new HttpGet(url);
      if(null != headers && headers.length > 0) {
        get.setHeaders(headers);
      }
      HttpClientUtil.config(get);

      response = httpclient.execute(get, HttpClientContext.create());

      int status = response.getStatusLine().getStatusCode();
      if (status >= 200 && status < 300) {
        HttpEntity entity = response.getEntity();
        return entity != null ? EntityUtils.toString(entity) : null;
      } else {
        throw new ClientProtocolException("Unexpected response status: " + status);
      }
      }  catch (SocketTimeoutException se) {
        se.printStackTrace();
        throw new SocketTimeoutException("HttpClientUtil::post::exception occurs!");
      } catch (ConnectTimeoutException ce) {
        ce.printStackTrace();
        throw new ConnectTimeoutException("HttpClientUtil::post::exception occurs!");
      } catch (Exception e) {
        throw new Exception("HttpClientUtil::post::exception occurs!", e);
      }
    finally {
      if (response != null) {
        try {
          EntityUtils.consume(response.getEntity());
          response.close();
        } catch (Exception e2) {
        }
      }
    }

  }

  private byte[] invoke(CloseableHttpClient httpclient, HttpUriRequest request) throws Exception
  {

    CloseableHttpResponse response = httpclient.execute(request);
    try
    {
      HttpEntity entity = response.getEntity();

      if(null != entity)
      {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try
        {
          entity.writeTo(os);
          os.flush();

          return os.toByteArray();
        }
        finally
        {
          os.close();
        }
      }
    }
    finally
    {
      response.close();
    }
    return null;
  }

  public void getPoolStatus() {
    System.out.println("max=" + this.httpClientManager.getTotalStats().getMax());
    System.out.println("available=" + this.httpClientManager.getTotalStats().getAvailable());
    System.out.println("leased=" + this.httpClientManager.getTotalStats().getLeased());
  }

  private static TrustManager trustAllManager = new X509TrustManager() {
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }

    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
        throws CertificateException {

    }

    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
        throws CertificateException {

    }
  };
}
