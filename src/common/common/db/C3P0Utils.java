package common.db;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import common.config.Config;

/**
 * @Description C3P0 工具类
 */
public class C3P0Utils {
	private static DataSource ds;

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	static {
		//加载c3p0连接池配置  
		System.setProperty("com.mchange.v2.c3p0.cfg.xml",Config.DB_CONFIG);
		ds = new ComboPooledDataSource();
	}

	public static DataSource getDataSource() {
		return ds;
	}

	public static Connection getConnection() throws SQLException {
		try {
			Connection conn = tl.get();
			if (conn == null) { 
				conn = ds.getConnection();
				tl.set(conn);
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void startTransaction() {
		try {
			Connection conn = tl.get();
			if (conn == null) {
				conn = ds.getConnection();
				tl.set(conn);
			}
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void commitTransaction() {
		try {
			Connection conn = tl.get();
			if (conn != null) {
				conn.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeConnection() {
		try {
			Connection conn = tl.get();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			tl.remove();
		}
	}
	
	public static void destroy(){
        try{
            //调用c3p0的关闭数据库连接的方法
            DataSources.destroy(ds);
            //等待连接池关闭线程退出，避免Tomcat报线程未关闭导致memory leak的错误
            Thread.sleep(1000);
            
            // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                	e.printStackTrace();
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}
}