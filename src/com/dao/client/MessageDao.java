package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.MessageBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 消息dao
 */
public class MessageDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param messageId
	 * @return
	 */
	public static MessageBean loadByMessageId(String messageId){
		MessageBean bean=null;
		try {
			bean=dbUtils.read(MessageBean.class, "where messageid=?", messageId);
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
	public static List<MessageBean> loadByUid(String uid){
		List<MessageBean> messageList=new ArrayList<MessageBean>();
		try {
			messageList=dbUtils.query(MessageBean.class, "where uid=?", uid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return messageList;
	}
	
	/**
	 * 加载倒序排序后相应数据
	 * @param count
	 * @return
	 */
	public static MessageBean loadByCount(int count){
		MessageBean bean=null;
		try {
			bean=dbUtils.read(MessageBean.class, "order by id desc limit ?", count);
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
	public static List<MessageBean> loadAllMessage(){
		List<MessageBean> messageList=new ArrayList<MessageBean>();
		try {
			messageList=dbUtils.query(MessageBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return messageList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<MessageBean> loadAllMessage(int index, int size){
		List<MessageBean> messageList=new ArrayList<MessageBean>();
		try {
			messageList=dbUtils.query(MessageBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return messageList;
	}
	
	public static List<MessageBean> loadMessageByUid(int index, int size, String uid){
		List<MessageBean> messageList=new ArrayList<MessageBean>();
		try {
			messageList=dbUtils.query(MessageBean.class, 
					" where uid=? order by id desc limit ?,?", uid, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return messageList;
	}
	
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<MessageBean> loadAllShop4page(int pageNum, int pageSize){
		List<MessageBean> messageList=new ArrayList<MessageBean>();
		try {
			messageList=dbUtils.query(MessageBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return messageList;
	}
	public static List<MessageBean> loadShop4type(int pageNum, int pageSize, int type){
		List<MessageBean> messageList=new ArrayList<MessageBean>();
		try {
			messageList=dbUtils.query(MessageBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return messageList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(MessageBean.class, "select COUNT(*) from message");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(MessageBean.class, 
					"select COUNT(*) from message where type=?", type);
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
	public static int save(MessageBean bean){
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
	public static int update(MessageBean bean){
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
			return dbUtils.delete(MessageBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
