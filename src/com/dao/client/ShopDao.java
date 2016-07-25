package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.ShopBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 商店dao
 */
public class ShopDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param shopid
	 * @return
	 */
	public static ShopBean loadByShopId(long shopid){
		ShopBean bean=null;
		try {
			bean=dbUtils.read(ShopBean.class, "where shopid=?", shopid);
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
	public static ShopBean loadByShopname(String username){
		ShopBean bean=null;
		try {
			bean=dbUtils.read(ShopBean.class, "where username=?", username);
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
	public static ShopBean loadByCount(int count){
		ShopBean bean=null;
		try {
			bean=dbUtils.read(ShopBean.class, "order by id desc limit ?", count);
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
	public static List<ShopBean> loadAllShop(){
		List<ShopBean> Shoplist=new ArrayList<ShopBean>();
		try {
			Shoplist=dbUtils.query(ShopBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Shoplist;
	}
	
	/**
	 * 加载所有列表
	 * @param ShopType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<ShopBean> loadAllShop(int pageNum, int pageSize){
		List<ShopBean> Shoplist=new ArrayList<ShopBean>();
		try {
			Shoplist=dbUtils.query(ShopBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Shoplist;
	}
	public static List<ShopBean> loadShop4type(int pageNum, int pageSize, int type){
		List<ShopBean> Shoplist=new ArrayList<ShopBean>();
		try {
			Shoplist=dbUtils.query(ShopBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Shoplist;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int ShopCount=0;
		try {
			ShopCount=dbUtils.stat(ShopBean.class, "select COUNT(*) from Shop_gw");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return ShopCount;
	}
	public static int Count(int type){
		int ShopCount=0;
		try {
			ShopCount=dbUtils.stat(ShopBean.class, 
					"select COUNT(*) from Shop_gw where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return ShopCount;
	}
	
	/**
	 * 插入列表数据
	 * @param 
	 * @return 
	 */
	public static int save(ShopBean bean){
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
	public static int update(ShopBean bean){
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
			return dbUtils.delete(ShopBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
