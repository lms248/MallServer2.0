package dao.client;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.client.CommentBean;

import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * 评论dao
 */
public class CommentDao {
	private static Logger log=LoggerManager.getLogger();
	
	/**
	 * 加载数据
	 * @param commentId
	 * @return
	 */
	public static CommentBean loadByCommentId(String commentId){
		CommentBean bean=null;
		try {
			bean=dbUtils.read(CommentBean.class, "where commentid=?", commentId);
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
	public static CommentBean loadByUid(String uid){
		CommentBean bean=null;
		try {
			bean=dbUtils.read(CommentBean.class, "where uid=?", uid);
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
	public static CommentBean loadByCount(int count){
		CommentBean bean=null;
		try {
			bean=dbUtils.read(CommentBean.class, " comment by id desc limit ?", count);
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
	public static List<CommentBean> loadAllComment(){
		List<CommentBean> commentList=new ArrayList<CommentBean>();
		try {
			commentList=dbUtils.query(CommentBean.class, " comment by id desc");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return commentList;
	}
	
	/**
	 * 加载对应区间列表
	 * 
	 * @param index
	 * @param size
	 * @return
	 */
	public static List<CommentBean> loadAllComment(int index, int size){
		List<CommentBean> commentList=new ArrayList<CommentBean>();
		try {
			commentList=dbUtils.query(CommentBean.class, 
					" order by id desc limit ?,?", index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return commentList;
	}
	public static List<CommentBean> loadAllCommentForGoodsId(String goodsId, int index, int size){
		List<CommentBean> commentList=new ArrayList<CommentBean>();
		try {
			commentList=dbUtils.query(CommentBean.class, 
					" where goodsid=? order by id desc limit ?,?",goodsId, index, size);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return commentList;
	}
	
	/**
	 * 查询表的数据总数量
	 * @param
	 * @return int
	 */
	public static int Count(){
		int amount=0;
		try {
			amount=dbUtils.stat(CommentBean.class, "select COUNT(*) from comment");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return amount;
	}
	public static int Count(int type){
		int amount=0;
		try {
			amount=dbUtils.stat(CommentBean.class, 
					"select COUNT(*) from comment where type=?", type);
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
	public static int save(CommentBean bean){
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
	public static int update(CommentBean bean){
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
			return dbUtils.delete(CommentBean.class, id);
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
	public static int deleteByCommentId(String commentId){
		try {
			return dbUtils.delete(CommentBean.class, "commentid", commentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
