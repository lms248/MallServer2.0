package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.CollectBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 商品收藏dao
 */
public class CollectDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param collectId
	 * @return
	 */
	public static CollectBean loadByCollectId(long collectId){
		CollectBean bean=null;
		try {
			bean=dbUtils.read(CollectBean.class, "where collectid=?", collectId);
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
	public static List<CollectBean> loadByUid(long uid){
		List<CollectBean> collectList=new ArrayList<CollectBean>();
		try {
			collectList=dbUtils.query(CollectBean.class, "where uid=?", uid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return collectList;
	}
	
	public static List<CollectBean> loadByUid(long uid, int index, int size){
		List<CollectBean> collectList=new ArrayList<CollectBean>();
		try {
			collectList=dbUtils.query(CollectBean.class, "where uid=? order by id desc limit ?,?", uid, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return collectList;
	}
	
	public static CollectBean loadByUidAndGoodId(long uid, int goodsId){
		CollectBean bean=null;
		try {
			bean=dbUtils.read(CollectBean.class, "where uid=? and goodsId=?", uid, goodsId);
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
	public static CollectBean loadByCount(int count){
		CollectBean bean=null;
		try {
			bean=dbUtils.read(CollectBean.class, "order by id desc limit ?", count);
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
	public static List<CollectBean> loadAllCollect(){
		List<CollectBean> collectList=new ArrayList<CollectBean>();
		try {
			collectList=dbUtils.query(CollectBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return collectList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<CollectBean> loadAllCollect(int index, int size){
		List<CollectBean> collectList=new ArrayList<CollectBean>();
		try {
			collectList=dbUtils.query(CollectBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return collectList;
	}
	
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<CollectBean> loadAllShop4page(int pageNum, int pageSize){
		List<CollectBean> collectList=new ArrayList<CollectBean>();
		try {
			collectList=dbUtils.query(CollectBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return collectList;
	}
	
	public static List<CollectBean> loadShop4type(int pageNum, int pageSize, int type){
		List<CollectBean> collectList=new ArrayList<CollectBean>();
		try {
			collectList=dbUtils.query(CollectBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return collectList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(CollectBean.class, "select COUNT(*) from collect");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(CollectBean.class, 
					"select COUNT(*) from collect where type=?", type);
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
	public static int save(CollectBean bean){
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
	public static int update(CollectBean bean){
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
			return dbUtils.delete(CollectBean.class, id);
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
	public static int deleteByCollectId(long collectId){
		try {
			return dbUtils.delete(CollectBean.class, "collectid" ,collectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
