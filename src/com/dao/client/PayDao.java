package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.PayBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 支付dao
 */
@Deprecated
public class PayDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param payId
	 * @return
	 */
	public static PayBean loadByPayId(String payId){
		PayBean bean=null;
		try {
			bean=dbUtils.read(PayBean.class, "where payid=?", payId);
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
	public static PayBean loadByUid(String uid){
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
			bean=dbUtils.read(PayBean.class, " order by id desc limit ?", count);
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
		List<PayBean> payList=new ArrayList<PayBean>();
		try {
			payList=dbUtils.query(PayBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return payList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<PayBean> loadAllPay(int index, int size){
		List<PayBean> payList=new ArrayList<PayBean>();
		try {
			payList=dbUtils.query(PayBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return payList;
	}
	
	public static List<PayBean> loadPayByStatus(int index, int size, int status){
		List<PayBean> payList=new ArrayList<PayBean>();
		try {
			payList=dbUtils.query(PayBean.class, 
					" where status=? order by id desc limit ?,?", status, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return payList;
	}
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(PayBean.class, "select COUNT(*) from pay");
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
					"select COUNT(*) from pay where type=?", type);
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
	public static int deleteByPayId(String payId){
		try {
			return dbUtils.delete(PayBean.class, "payid", payId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
