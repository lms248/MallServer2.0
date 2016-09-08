package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.OrderBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 订单dao
 */
public class OrderDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param orderId
	 * @return
	 */
	public static OrderBean loadByOrderId(long orderId){
		OrderBean bean=null;
		try {
			bean=dbUtils.read(OrderBean.class, "where orderid=?", orderId);
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
	public static OrderBean loadByUid(long uid){
		OrderBean bean=null;
		try {
			bean=dbUtils.read(OrderBean.class, "where uid=?", uid);
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
	public static OrderBean loadByCount(int count){
		OrderBean bean=null;
		try {
			bean=dbUtils.read(OrderBean.class, "order by id desc limit ?", count);
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
	public static List<OrderBean> loadAllOrder(){
		List<OrderBean> orderList=new ArrayList<OrderBean>();
		try {
			orderList=dbUtils.query(OrderBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<OrderBean> loadAllOrder(int index, int size){
		List<OrderBean> orderList=new ArrayList<OrderBean>();
		try {
			orderList=dbUtils.query(OrderBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<OrderBean> loadAllShop4page(int pageNum, int pageSize){
		List<OrderBean> orderList=new ArrayList<OrderBean>();
		try {
			orderList=dbUtils.query(OrderBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	public static List<OrderBean> loadShop4type(int pageNum, int pageSize, int type){
		List<OrderBean> orderList=new ArrayList<OrderBean>();
		try {
			orderList=dbUtils.query(OrderBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(OrderBean.class, "select COUNT(*) from order");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(OrderBean.class, 
					"select COUNT(*) from order where type=?", type);
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
	public static int save(OrderBean bean){
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
	public static int update(OrderBean bean){
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
			return dbUtils.delete(OrderBean.class, id);
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
	public static int deleteByOrderId(long orderId){
		try {
			return dbUtils.delete(OrderBean.class, "orderid", orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
