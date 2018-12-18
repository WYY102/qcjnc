package com.zuoping.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
 	自定义JDBC工具类
 		1.加载驱动
 		2.获取连接对象
 		3.关闭连接
 		
 	把连接数据库需要的信息，都保存在一个文件中，这个文件是一个properties文件
 */
public class JDBCUtil {
	private static String url = null;
	private static String user = null;
	private static String password = null;
	private static String driverClass = null;
	private static InputStream in = null;
	
	//利用静态代码块的特征，在类文件加载到内存的时候，就会执行在静态代码块中的代码
	static {
		try {
			//1.读取配置文件信息 读取properties文件
			Properties props = new Properties();
			
			//加载一个在服务器上的properties文件
			in = JDBCUtil.class.getResourceAsStream("/db.properties");
			
			//2.利用Properties里面的load方法加载文件
			props.load(in);
			
			//3.可以通过Properties类对象，获取到想要的数据
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
			driverClass = props.getProperty("driver");
			
			//4.加载类文件
			Class.forName(driverClass);
			
			/*
			 	通过类路径方式读取
			 */
			} catch(IOException  e) {
				e.printStackTrace();
				System.out.println("驱动加载失败");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("驱动加载失败");
			}	finally {
				//关闭文件连接
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
	}
	
	/**
	 * 获取数据库连接对象
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn
	 * @param st
	 */
	public static void close(Connection conn, Statement st) {
		try {
			
			if(st != null) {
				st.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
			//糖衣炮弹
			throw new RuntimeException(e);
		}
	}
	
	public static void close(Connection conn, Statement st, ResultSet set) {
		try {
			
			if(st != null) {
				st.close();
			}
			if(conn != null) {
				conn.close();
			}
			if(set != null) {
				set.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
			//糖衣炮弹
			throw new RuntimeException(e);
		}
	}
	
}
