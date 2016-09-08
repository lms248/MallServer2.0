package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.FeedbackBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 反馈dao
 */
public class FeedbackDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param feedbackId
	 * @return
	 */
	public static FeedbackBean loadByFeedbackId(long feedbackId){
		FeedbackBean bean=null;
		try {
			bean=dbUtils.read(FeedbackBean.class, "where feedbackid=?", feedbackId);
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
	public static FeedbackBean loadByUid(long uid){
		FeedbackBean bean=null;
		try {
			bean=dbUtils.read(FeedbackBean.class, "where uid=?", uid);
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
	public static FeedbackBean loadByCount(int count){
		FeedbackBean bean=null;
		try {
			bean=dbUtils.read(FeedbackBean.class, "order by id desc limit ?", count);
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
	public static List<FeedbackBean> loadAllFeedback(){
		List<FeedbackBean> feedbackList=new ArrayList<FeedbackBean>();
		try {
			feedbackList=dbUtils.query(FeedbackBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return feedbackList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<FeedbackBean> loadAllFeedback(int index, int size){
		List<FeedbackBean> feedbackList=new ArrayList<FeedbackBean>();
		try {
			feedbackList=dbUtils.query(FeedbackBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return feedbackList;
	}
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<FeedbackBean> loadAllShop4page(int pageNum, int pageSize){
		List<FeedbackBean> feedbackList=new ArrayList<FeedbackBean>();
		try {
			feedbackList=dbUtils.query(FeedbackBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return feedbackList;
	}
	public static List<FeedbackBean> loadShop4type(int pageNum, int pageSize, int type){
		List<FeedbackBean> feedbackList=new ArrayList<FeedbackBean>();
		try {
			feedbackList=dbUtils.query(FeedbackBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return feedbackList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(FeedbackBean.class, "select COUNT(*) from feedback");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(FeedbackBean.class, 
					"select COUNT(*) from feedback where type=?", type);
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
	public static int save(FeedbackBean bean){
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
	public static int update(FeedbackBean bean){
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
			return dbUtils.delete(FeedbackBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
