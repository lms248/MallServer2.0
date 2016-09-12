package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.GoodssortBean;
import bean.client.ShopBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 商品分类dao
 */
public class GoodssortDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param goodssortId
	 * @return
	 */
	public static GoodssortBean loadByOrderId(long goodssortId){
		GoodssortBean bean=null;
		try {
			bean=dbUtils.read(GoodssortBean.class, "where goodssortid=?", goodssortId);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 加载数据
	 * @param goodsId
	 * @return
	 */
	public static List<GoodssortBean> loadByGoodsId(long goodsId){
		List<GoodssortBean> goodssortList=new ArrayList<GoodssortBean>();
		try {
			goodssortList=dbUtils.query(GoodssortBean.class, " where goodsid=?", goodsId);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodssortList;
	}
	
	/**
	 * 加载倒序排序后相应数据
	 * @param count
	 * @return
	 */
	public static GoodssortBean loadByCount(int count){
		GoodssortBean bean=null;
		try {
			bean=dbUtils.read(GoodssortBean.class, "goodssort by id desc limit ?", count);
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
	public static List<GoodssortBean> loadAllGoodssort(){
		List<GoodssortBean> goodssortList=new ArrayList<GoodssortBean>();
		try {
			goodssortList=dbUtils.query(GoodssortBean.class, " goodssort by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodssortList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<GoodssortBean> loadAllGoodssort(int index, int size){
		List<GoodssortBean> goodssortList=new ArrayList<GoodssortBean>();
		try {
			goodssortList=dbUtils.query(GoodssortBean.class, 
					" goodssort by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodssortList;
	}
	
	public static List<GoodssortBean> loadByLevel_1(int level_1, int index, int size){
		List<GoodssortBean> goodssortList=new ArrayList<GoodssortBean>();
		try {
			goodssortList=dbUtils.query(GoodssortBean.class, 
					" where level_1=? order by id desc limit ?,?", level_1, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodssortList;
	}
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(GoodssortBean.class, "select COUNT(*) from goodssort");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(GoodssortBean.class, 
					"select COUNT(*) from goodssort where type=?", type);
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
	public static int save(GoodssortBean bean){
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
	public static int update(GoodssortBean bean){
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
			return dbUtils.delete(GoodssortBean.class, id);
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
	public static int deleteByGoodssortId(long goodssortId){
		try {
			return dbUtils.delete(GoodssortBean.class, "goodssortid", goodssortId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
