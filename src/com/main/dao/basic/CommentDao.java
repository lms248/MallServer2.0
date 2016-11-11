package main.dao.basic;

import java.util.List;

import main.bean.basic.CommentBean;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 评论dao
 */
@Repository
public interface CommentDao {
	
	public CommentBean loadById(int id);
	
	public CommentBean loadByCommentId(String commentId);
	
	public List<CommentBean> loadByGoodsId(
			@Param("goodsId")String goodsId, @Param("index")int index, @Param("size")int size);
	
	public List<CommentBean> loadByUidAndGoodsId(@Param("uid")String uid, @Param("goodsId")String goodsId);
	
	public List<CommentBean> loadAllComment(@Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(CommentBean bean);
	public int update(CommentBean bean);
	public int deleteByCommentId(String commentId);
}
