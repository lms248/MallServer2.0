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
	 * @param goodsid
	 * @return
	 */
	public static GoodsBean loadByGoodsId(long goodsid){
		GoodsBean bean=null;
		try {
			bean=dbUtils.read(GoodsBean.class, "where goodsid=?", goodsid);
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
		List<GoodsBean> Goodslist=new ArrayList<GoodsBean>();
		try {
			Goodslist=dbUtils.query(GoodsBean.class, "where type=?", type);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Goodslist;
	}
	
	/**
	 * 加载所有列表
	 * @return List
	 */
	public static List<GoodsBean> loadAllGoods(){
		List<GoodsBean> Goodslist=new ArrayList<GoodsBean>();
		try {
			Goodslist=dbUtils.query(GoodsBean.class, " order by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Goodslist;
	}
	
	/**
	 * 加载所有列表
	 * @param GoodsType ,pageNum(页码),pageSize(页数)
	 * @return List
	 */
	public static List<GoodsBean> loadAllGoods(int pageNum, int pageSize){
		List<GoodsBean> Goodslist=new ArrayList<GoodsBean>();
		try {
			Goodslist=dbUtils.query(GoodsBean.class, 
					" order by id desc limit ?,?", (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Goodslist;
	}
	public static List<GoodsBean> loadGoods4type(int type, int pageNum, int pageSize){
		List<GoodsBean> Goodslist=new ArrayList<GoodsBean>();
		try {
			Goodslist=dbUtils.query(GoodsBean.class, 
					" where type=? order by id desc limit ?,?", type, (pageNum-1)*pageSize, pageSize);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return Goodslist;
	}
	
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int GoodsCount=0;
		try {
			GoodsCount=dbUtils.stat(GoodsBean.class, "select COUNT(*) from Goods_gw");
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
					"select COUNT(*) from Goods_gw where type=?", type);
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
}