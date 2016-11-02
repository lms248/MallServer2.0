package dao.old;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.basic.OrdersBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 订单dao
 */
@Deprecated
public class OrdersDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param orderId
	 * @return
	 */
	public static OrdersBean loadByOrderId(String orderId){
		OrdersBean bean=null;
		try {
			bean=dbUtils.read(OrdersBean.class, "where orderid=?", orderId);
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
	public static List<OrdersBean> loadByUid(String uid){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, "where uid=?", uid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	
	public static List<OrdersBean> loadByUidAndStatus(String uid, int status){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, "where uid=? and status=?", uid, status);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	
	public static List<OrdersBean> loadByPayId(String payId){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, "where payid=?", payId);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	
	/**
	 * 加载倒序排序后相应数据
	 * @param count
	 * @return
	 */
	public static OrdersBean loadByCount(int count){
		OrdersBean bean=null;
		try {
			bean=dbUtils.read(OrdersBean.class, "order by id desc limit ?", count);
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
	public static List<OrdersBean> loadAllOrder(){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, " order by id desc");
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
	public static List<OrdersBean> loadAllOrder(int index, int size){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	
	public static List<OrdersBean> loadOrderByStatus(int index, int size, int status){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, 
					" where status=? order by id desc limit ?,?", status, index, size);
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
	public static List<OrdersBean> loadAllShop4page(int pageNum, int pageSize){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return orderList;
	}
	public static List<OrdersBean> loadShop4type(int pageNum, int pageSize, int type){
		List<OrdersBean> orderList=new ArrayList<OrdersBean>();
		try {
			orderList=dbUtils.query(OrdersBean.class, 
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
			amount=dbUtils.stat(OrdersBean.class, "select COUNT(*) from orders");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int status){
		int amount=0;
		try {
			amount=dbUtils.stat(OrdersBean.class, 
					"select COUNT(*) from orders where status=?", status);
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
	public static int save(OrdersBean bean){
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
	public static int update(OrdersBean bean){
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
			return dbUtils.delete(OrdersBean.class, id);
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
			return dbUtils.delete(OrdersBean.class, "orderid", orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
