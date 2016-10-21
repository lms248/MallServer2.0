package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.GoodsBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 商品dao
 */
public class GoodsDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param goodsId
	 * @return
	 */
	public static GoodsBean loadByGoodsId(String goodsId){
		GoodsBean bean=null;
		try {
			bean=dbUtils.read(GoodsBean.class, "where goodsid=?", goodsId);
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
	public static GoodsBean loadByCount(int count){
		GoodsBean bean=null;
		try {
			bean=dbUtils.read(GoodsBean.class, "order by id desc limit ?", count);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 加载数据
	 * @param type
	 * @return
	 */
	public static List<GoodsBean> loadByType(int type){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, "where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	/**
	 * 加载所有列表
	 * @return List
	 */
	public static List<GoodsBean> loadAllGoods(){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<GoodsBean> loadAllGoods(int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoods_search(String searchContent, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where (goodsId like ? or name like ?) order by id desc limit ?,?", "%"+searchContent+"%", "%"+searchContent+"%", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoodsForSort(int sortId, int sortPId, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where sortId=? or sortId in (select id from sort where pid=?) order by id desc limit ?,?", sortId, sortPId, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoodsForSort_search(int sortId, int sortPId, String searchContent, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where (sortId=? or sortId in (select id from sort where pid=?))"
					+ " and goodsId like ? or name like ? order by id desc limit ?,?", sortId, sortPId, "%"+searchContent+"%", "%"+searchContent+"%", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoodsForShop(long shopId, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where shopId=? order by id desc limit ?,?", shopId, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoodsForShop_search(long shopId, String searchContent, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where shopId=? and (goodsId like ? or name like ?) order by id desc limit ?,?", 
					shopId, "%"+searchContent+"%", "%"+searchContent+"%", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoodsForShopAndSort(long shopId, int sortId, int sortPId, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where shopId=? and (sortId=? or sortId in (select id from sort where pid=?)) order by id desc limit ?,?", 
					shopId, sortId, sortPId, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public static List<GoodsBean> loadAllGoodsForShopAndSort_search(
			long shopId, int sortId, int sortPId, String searchContent, int index, int size){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where shopId=? and (sortId=? or sortId in (select id from sort where pid=?))"
					+ " and (goodsId like ? or name like ?) order by id desc limit ?,?", 
					shopId, sortId, sortPId, "%"+searchContent+"%", "%"+searchContent+"%", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	/**
	 * 加载所有列表
	 * @param GoodsType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<GoodsBean> loadAllGoods4page(int pageNum, int pageSize){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	public static List<GoodsBean> loadGoods4type(int type, int pageNum, int pageSize){
		List<GoodsBean> goodsList=new ArrayList<GoodsBean>();
		try {
			goodsList=dbUtils.query(GoodsBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return goodsList;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int GoodsCount=0;
		try {
			GoodsCount=dbUtils.stat(GoodsBean.class, "select COUNT(*) from goods");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return GoodsCount;
	}
	public static int Count(int type){
		int GoodsCount=0;
		try {
			GoodsCount=dbUtils.stat(GoodsBean.class, 
					"select COUNT(*) from goods where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return GoodsCount;
	}
	
	/**
	 * 插入列表数据
	 * @param 
	 * @return 
	 */
	public static int save(GoodsBean bean){
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
	public static int update(GoodsBean bean){
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
			return dbUtils.delete(GoodsBean.class, id);
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
	public static int deleteByGoodsId(String goodsId){
		try {
			return dbUtils.delete(GoodsBean.class, "goodsid" ,goodsId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
