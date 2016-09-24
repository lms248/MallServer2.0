package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.PayBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 订单dao
 */
public class PayDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param PayId
	 * @return
	 */
	public static PayBean loadByPayId(long PayId){
		PayBean bean=null;
		try {
			bean=dbUtils.read(PayBean.class, "where Payid=?", PayId);
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
	public static PayBean loadByUid(long uid){
		PayBean bean=null;
		try {
			bean=dbUtils.read(PayBean.class, "where uid=?", uid);
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
	public static PayBean loadByCount(int count){
		PayBean bean=null;
		try {
			bean=dbUtils.read(PayBean.class, "Pay by id desc limit ?", count);
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
	public static List<PayBean> loadAllPay(){
		List<PayBean> PayList=new ArrayList<PayBean>();
		try {
			PayList=dbUtils.query(PayBean.class, " Pay by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return PayList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<PayBean> loadAllPay(int index, int size){
		List<PayBean> PayList=new ArrayList<PayBean>();
		try {
			PayList=dbUtils.query(PayBean.class, 
					" Pay by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return PayList;
	}
	/**
	 * 加载所有列表
	 * @param type ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<PayBean> loadAllShop4page(int pageNum, int pageSize){
		List<PayBean> PayList=new ArrayList<PayBean>();
		try {
			PayList=dbUtils.query(PayBean.class, 
					" Pay by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return PayList;
	}
	public static List<PayBean> loadShop4type(int pageNum, int pageSize, int type){
		List<PayBean> PayList=new ArrayList<PayBean>();
		try {
			PayList=dbUtils.query(PayBean.class, 
					" where type=? Pay by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return PayList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(PayBean.class, "select COUNT(*) from Pay");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(PayBean.class, 
					"select COUNT(*) from Pay where type=?", type);
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
	public static int save(PayBean bean){
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
	public static int update(PayBean bean){
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
			return dbUtils.delete(PayBean.class, id);
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
	public static int deleteByPayId(long PayId){
		try {
			return dbUtils.delete(PayBean.class, "Payid", PayId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
