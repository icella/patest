package ice.utils.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ice.AppConfig;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

/**
 * 数据源
 * @author lla
 *
 */
public class DBconnFactory {
  private DruidDataSource dds = null;
  private Dao dao = null;

  public static DBconnFactory getInstance() {
    return DBconnDHolder.instance;
  }

  private static class DBconnDHolder {
    private static DBconnFactory instance = new DBconnFactory();
  }

  private DBconnFactory() {
    initDruidDataSourceFactory();
  }

  private void initDruidDataSourceFactory() {
    try {
      dds = (DruidDataSource) DruidDataSourceFactory
          .createDataSource(AppConfig.getInstance().getProperties("dbconfig.properties"));
      dao = new NutDao(dds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public DruidDataSource getDds() {
    return dds;
  }
  
  public Dao getDao() {
    return dao;
  }

  public static void colseConnQuietly(DruidPooledConnection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void colseStmtQuietly(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void colseRsQuietly(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
