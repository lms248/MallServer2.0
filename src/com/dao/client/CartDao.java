package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.CartBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 购物车dao
 */
public class CartDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param cartId
	 * @return
	 */
	public static CartBean loadByCartId(long cartId){
		CartBean bean=null;
		try {
			bean=dbUtils.read(CartBean.class, "where cartid=?", cartId);
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
	public static CartBean loadByUid(long uid){
		CartBean bean=null;
		try {
			bean=dbUtils.read(CartBean.class, "where uid=?", uid);
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
	public static CartBean loadByCount(int count){
		CartBean bean=null;
		try {
			bean=dbUtils.read(CartBean.class, "order by id desc limit ?", count);
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
	public static List<CartBean> loadAllCart(){
		List<CartBean> cartList=new ArrayList<CartBean>();
		try {
			cartList=dbUtils.query(CartBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return cartList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<CartBean> loadAllCart(int index, int size){
		List<CartBean> cartList=new ArrayList<CartBean>();
		try {
			cartList=dbUtils.query(CartBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return cartList;
	}
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<CartBean> loadAllShop4page(int pageNum, int pageSize){
		List<CartBean> cartList=new ArrayList<CartBean>();
		try {
			cartList=dbUtils.query(CartBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return cartList;
	}
	public static List<CartBean> loadShop4type(int pageNum, int pageSize, int type){
		List<CartBean> cartList=new ArrayList<CartBean>();
		try {
			cartList=dbUtils.query(CartBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return cartList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(CartBean.class, "select COUNT(*) from cart");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(CartBean.class, 
					"select COUNT(*) from Shop_gw where type=?", type);
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
	public static int save(CartBean bean){
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
	public static int update(CartBean bean){
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
			return dbUtils.delete(CartBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
