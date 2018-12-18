package com.zuoping.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/*
 通用的DAO层，主要负责数据库的增删改查
 这个类定义两个方法：
 	1.数据更新（增删改）
 	2.数据查询（查）
 */
public class BaseDao {
	//初始化参数
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	/**
	 * 主要针对增删改的通用方法
	 * @param sql 要执行的SQL语句
	 * @param paramsValue	参数数组，用来处理SQL语句中的占位符参数，如果没有，请传入null
	 */
	public void update(String sql, Object[] paramsValue) {
		try {
			//1.数据库连接
			conn = JDBCUtil.getConnection();
			//2.获取PreparedStatement
			pstmt = conn.prepareStatement(sql);
			
			//得到参数元数据个数
			int count = pstmt.getParameterMetaData().getParameterCount();
			//3.利用参数元数据给SQL语句的占位符需要的参数赋值
			if(paramsValue != null && paramsValue.length > 0) {
				for(int i = 0; i < count; i++) {
					//循环结束，就是可以给SQL语句完整赋值
					pstmt.setObject(i + 1, paramsValue[i]);
				}
			}
			
			//4.执行
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
	}
	
	/**
	 * 查询的通用方法
	 * @param sql 查询需要的SQL语句
	 * @param paramsValue 查询需要的参数，如果没有参数，设置为null
	 * @param cls List集合中保存的数据类型
	 * @return List集合，返回一个带有指定数据类型List集合
	 */
	public <T> List<T> query(String sql, Object[] paramsValue, Class<T> cls){
		
			try {
				//1.返回的数据集合
				List<T> list = new ArrayList<T>();
				
				//2.确定List集合中要保存的对象
				T t = null;
				
				//3.连接数据库
				conn = JDBCUtil.getConnection();
				pstmt = conn.prepareStatement(sql);
				//4.给SQL语句的站位符赋值
				if(paramsValue != null && paramsValue.length > 0) {
					for( int i = 0 ; i < pstmt.getParameterMetaData().getParameterCount(); i++) {
						pstmt.setObject(i + 1, paramsValue[i]);
					}
				}
				
				//5.执行查询操作，返回ResultSet
				rs = pstmt.executeQuery();
				
				//6.获取结果集元数据
				ResultSetMetaData rsMetaData = rs.getMetaData();
				//数据库列的数量
				int columnCount = rsMetaData.getColumnCount();
				
				//7.遍历ResultSet数据集
				while(rs.next()) {
					//创建要保存的对象
					t = cls.newInstance();
					
					//8.遍历数据行的每一列，得到每一列的名字，再获取到数据，保存到T对象中
					for(int i = 0; i < columnCount; i++) {
						//获取每一列的名字
						String columnName = rsMetaData.getColumnName(i + 1);
						//获取每一列的数据
						Object value = rs.getObject(columnName);
						
						//利用BeanUtils给T对象赋值
						BeanUtils.setProperty(t, columnName, value);
					}
					
					//把创建好的T对象，放入到List集合中
					list.add(t);
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(conn, pstmt, rs);
			}
			
			
		
		return null;
	}
}
