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
@Deprecated
public class UserDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param uid
	 * @return
	 */
	public static UserBean loadByUid(String uid){
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
	 * @param type
	 * @return
	 */
	public static UserBean loadByUidAndType(String uid, int type){
		UserBean bean=null;
		try {
			bean=dbUtils.read(UserBean.class, "where uid=? and type=?", uid, type);
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
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<UserBean> loadAllUser(int index, int size){
		List<UserBean> userList=new ArrayList<UserBean>();
		try {
			userList=dbUtils.query(UserBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return userList;
	}
	
	public static List<UserBean> loadAllUser_search(String searchContent, int index, int size){
		List<UserBean> userList=new ArrayList<UserBean>();
		try {
			userList=dbUtils.query(UserBean.class, 
					" where (uid like ? or username like ?) order by id desc limit ?,?", "%"+searchContent+"%", "%"+searchContent+"%", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return userList;
	}
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int UserCount=0;
		try {
			UserCount=dbUtils.stat(UserBean.class, "select COUNT(*) from user");
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
