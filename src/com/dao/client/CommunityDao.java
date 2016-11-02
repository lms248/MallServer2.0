package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.CommunityBean;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 社区dao
 */
@Deprecated
public class CommunityDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param communityId
	 * @return
	 */
	public static CommunityBean loadByCommunityId(long communityId){
		CommunityBean bean=null;
		try {
			bean=dbUtils.read(CommunityBean.class, "where communityid=?", communityId);
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
	public static CommunityBean loadByCommunityname(String username){
		CommunityBean bean=null;
		try {
			bean=dbUtils.read(CommunityBean.class, "where username=?", username);
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
	public static CommunityBean loadByCount(int count){
		CommunityBean bean=null;
		try {
			bean=dbUtils.read(CommunityBean.class, "order by id desc limit ?", count);
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
	public static List<CommunityBean> loadAllCommunity(){
		List<CommunityBean> Communitylist=new ArrayList<CommunityBean>();
		try {
			Communitylist=dbUtils.query(CommunityBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Communitylist;
	}
	
	/**
	 * 加载所有列表
	 * @param CommunityType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<CommunityBean> loadAllCommunity4page(int pageNum, int pageSize){
		List<CommunityBean> Communitylist=new ArrayList<CommunityBean>();
		try {
			Communitylist=dbUtils.query(CommunityBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Communitylist;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<CommunityBean> loadAllCommunity(int index, int size){
		List<CommunityBean> Communitylist=new ArrayList<CommunityBean>();
		try {
			Communitylist=dbUtils.query(CommunityBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Communitylist;
	}
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int CommunityCount=0;
		try {
			CommunityCount=dbUtils.stat(CommunityBean.class, "select COUNT(*) from Community_gw");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return CommunityCount;
	}
	public static int Count(int type){
		int CommunityCount=0;
		try {
			CommunityCount=dbUtils.stat(CommunityBean.class, 
					"select COUNT(*) from community where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return CommunityCount;
	}
	
	/**
	 * 插入列表数据
	 * @param 
	 * @return 
	 */
	public static int save(CommunityBean bean){
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
	public static int update(CommunityBean bean){
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
			return dbUtils.delete(CommunityBean.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
