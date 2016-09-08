package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.UserBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 用户dao
 */
public class UserDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param uid
	 * @return
	 */
	public static UserBean loadByUid(long uid){
		UserBean bean=null;
		try {
			bean=dbUtils.read(UserBean.class, "where uid=?", uid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 加载数据
	 * @param uid
	 * @return
	 */
	public static UserBean loadByUsername(String username){
		UserBean bean=null;
		try {
			bean=dbUtils.read(UserBean.class, "where username=?", username);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 加载数据
	 * @param uid
	 * @return
	 */
	public static UserBean loadByToken(String token){
		UserBean bean=null;
		try {
			bean=dbUtils.read(UserBean.class, "where token=?", token);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 加载倒序排序后相应数据
	 * @param count
	 * @return
	 */
	public static UserBean loadByCount(int count){
		UserBean bean=null;
		try {
			bean=dbUtils.read(UserBean.class, "order by id desc limit ?", count);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
			
	/**
	 * 加载所有列表
	 * @return List
	 */
	public static List<UserBean> loadAllUser(){
		List<UserBean> Userlist=new ArrayList<UserBean>();
		try {
			Userlist=dbUtils.query(UserBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Userlist;
	}
	
	/**
	 * 加载所有列表
	 * @param UserType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<UserBean> loadAllUser(int pageNum, int pageSize){
		List<UserBean> Userlist=new ArrayList<UserBean>();
		try {
			Userlist=dbUtils.query(UserBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Userlist;
	}
	public static List<UserBean> loadUser4type(int pageNum, int pageSize, int type){
		List<UserBean> Userlist=new ArrayList<UserBean>();
		try {
			Userlist=dbUtils.query(UserBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Userlist;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int UserCount=0;
		try {
			UserCount=dbUtils.stat(UserBean.class, "select COUNT(*) from User_gw");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return UserCount;
	}
	public static int Count(int type){
		int UserCount=0;
		try {
			UserCount=dbUtils.stat(UserBean.class, 
					"select COUNT(*) from user where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return UserCount;
	}
	
	/**
	 * 插入列表数据
	 * @param 
	 * @return 
	 */
	public static int save(UserBean bean){
		try {
			return dbUtils.insert(bean);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 更新列表数据
	 * @param 
	 * @return 
	 */
	public static int update(UserBean bean){
		try {
			return dbUtils.update(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 删除某条列表数据
	 * @param 
	 * @return 
	 */
	public static int delete(int id){
		try {
			return dbUtils.delete(UserBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
