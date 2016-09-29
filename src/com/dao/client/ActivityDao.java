package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.ActivityBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 活动dao
 */
public class ActivityDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param activityId
	 * @return
	 */
	public static ActivityBean loadByActivityId(String activityId){
		ActivityBean bean=null;
		try {
			bean=dbUtils.read(ActivityBean.class, "where activityid=?", activityId);
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
	public static ActivityBean loadByUid(long uid){
		ActivityBean bean=null;
		try {
			bean=dbUtils.read(ActivityBean.class, "where uid=?", uid);
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
	public static ActivityBean loadByCount(int count){
		ActivityBean bean=null;
		try {
			bean=dbUtils.read(ActivityBean.class, "order by id desc limit ?", count);
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
	public static List<ActivityBean> loadAllActivity(){
		List<ActivityBean> activityList=new ArrayList<ActivityBean>();
		try {
			activityList=dbUtils.query(ActivityBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return activityList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<ActivityBean> loadAllActivity(int index, int size){
		List<ActivityBean> activityList=new ArrayList<ActivityBean>();
		try {
			activityList=dbUtils.query(ActivityBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return activityList;
	}
	
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<ActivityBean> loadAllShop4page(int pageNum, int pageSize){
		List<ActivityBean> activityList=new ArrayList<ActivityBean>();
		try {
			activityList=dbUtils.query(ActivityBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return activityList;
	}
	
	public static List<ActivityBean> loadActivityForSort(int sortId, int sortPId, int index, int size){
		List<ActivityBean> activityList=new ArrayList<ActivityBean>();
		try {
			activityList=dbUtils.query(ActivityBean.class, 
					" where sortId=? or sortId in (select id from sort where pid=?) order by id desc limit ?,?",sortId, sortPId, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return activityList;
	}
	
	public static List<ActivityBean> loadShop4type(int pageNum, int pageSize, int type){
		List<ActivityBean> activityList=new ArrayList<ActivityBean>();
		try {
			activityList=dbUtils.query(ActivityBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return activityList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(ActivityBean.class, "select COUNT(*) from activity");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(ActivityBean.class, 
					"select COUNT(*) from activity where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	
	/**
	 * 插入列表数据
	 * @param 
	 * @return 
	 */
	public static int save(ActivityBean bean){
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
	public static int update(ActivityBean bean){
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
			return dbUtils.delete(ActivityBean.class, id);
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
	public static int deleteByActivityId(String activityId){
		try {
			return dbUtils.delete(ActivityBean.class, "activityid", activityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
