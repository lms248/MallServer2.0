package dao.old;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.basic.ShopBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 商店dao
 */
@Deprecated
public class ShopDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param shopId
	 * @return
	 */
	public static ShopBean loadByShopId(String shopId){
		ShopBean bean=null;
		try {
			bean=dbUtils.read(ShopBean.class, "where shopid=?", shopId);
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
	public static ShopBean loadByShopname(String name){
		ShopBean bean=null;
		try {
			bean=dbUtils.read(ShopBean.class, "where name=?", name);
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
		List<ShopBean> shopList=new ArrayList<ShopBean>();
		try {
			shopList=dbUtils.query(ShopBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return shopList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<ShopBean> loadAllShop(int index, int size){
		List<ShopBean> shopList=new ArrayList<ShopBean>();
		try {
			shopList=dbUtils.query(ShopBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return shopList;
	}
	
	public static List<ShopBean> loadAllShop_search(String searchContent, int index, int size){
		List<ShopBean> shopList=new ArrayList<ShopBean>();
		try {
			shopList=dbUtils.query(ShopBean.class, 
					" where (shopId like ? or name like ?) order by id desc limit ?,?", 
					"%"+searchContent+"%", "%"+searchContent+"%", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return shopList;
	}
	
	/**
	 * 加载所有列表
	 * @param ShopType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<ShopBean> loadAllShop4page(int pageNum, int pageSize){
		List<ShopBean> shopList=new ArrayList<ShopBean>();
		try {
			shopList=dbUtils.query(ShopBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return shopList;
	}
	public static List<ShopBean> loadShop4type(int pageNum, int pageSize, int type){
		List<ShopBean> shopList=new ArrayList<ShopBean>();
		try {
			shopList=dbUtils.query(ShopBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return shopList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int ShopCount=0;
		try {
			ShopCount=dbUtils.stat(ShopBean.class, "select COUNT(*) from shop");
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
					"select COUNT(*) from shop where type=?", type);
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
	
	/**
	 * 删除某条列表数据
	 * @param 
	 * @return 
	 */
	public static int deleteByShopId(String shopId){
		try {
			return dbUtils.delete(ShopBean.class, "shopid", shopId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
