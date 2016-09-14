package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.SortBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 商品分类dao
 */
public class SortDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param sortId
	 * @return
	 */
	public static SortBean loadById(int id){
		SortBean bean=null;
		try {
			bean=dbUtils.read(SortBean.class, "where id=?", id);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static List<SortBean> loadByPid(int pid){
		List<SortBean> sortList=new ArrayList<SortBean>();
		try {
			sortList=dbUtils.query(SortBean.class, " where pid=?", pid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return sortList;
	}
	
	/**
	 * 加载数据
	 * @param uid
	 * @return
	 */
	public static SortBean loadBySortname(String name){
		SortBean bean=null;
		try {
			bean=dbUtils.read(SortBean.class, "where name=?", name);
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
	public static SortBean loadByCount(int count){
		SortBean bean=null;
		try {
			bean=dbUtils.read(SortBean.class, "order by id desc limit ?", count);
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
	public static List<SortBean> loadAllSort(){
		List<SortBean> sortList=new ArrayList<SortBean>();
		try {
			sortList=dbUtils.query(SortBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return sortList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<SortBean> loadAllSort(int index, int size){
		List<SortBean> sortList=new ArrayList<SortBean>();
		try {
			sortList=dbUtils.query(SortBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return sortList;
	}
	/**
	 * 加载所有列表
	 * @param SortType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<SortBean> loadAllSort4page(int pageNum, int pageSize){
		List<SortBean> sortList=new ArrayList<SortBean>();
		try {
			sortList=dbUtils.query(SortBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return sortList;
	}
	public static List<SortBean> loadSort4type(int pageNum, int pageSize, int type){
		List<SortBean> sortList=new ArrayList<SortBean>();
		try {
			sortList=dbUtils.query(SortBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return sortList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int SortCount=0;
		try {
			SortCount=dbUtils.stat(SortBean.class, "select COUNT(*) from sort");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return SortCount;
	}
	public static int Count(int type){
		int SortCount=0;
		try {
			SortCount=dbUtils.stat(SortBean.class, 
					"select COUNT(*) from sort where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return SortCount;
	}
	
	/**
	 * 插入列表数据
	 * @param 
	 * @return 
	 */
	public static int save(SortBean bean){
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
	public static int update(SortBean bean){
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
			return dbUtils.delete(SortBean.class, id);
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
	public static int deleteBySortId(long sortId){
		try {
			return dbUtils.delete(SortBean.class, "sortid", sortId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
