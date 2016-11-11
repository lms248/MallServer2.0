package main.dao.basic;

import java.util.List;

import main.bean.basic.CollectBean;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 收藏dao
 */
@Repository
public interface CollectDao {
	
	public CollectBean loadById(int id);
	
	public CollectBean loadByCollectId(String collectId);
	
	public CollectBean loadByUidAndGoodId(@Param("uid")String uid, @Param("goodsId")String goodsId);
	
	public List<CollectBean> loadByUid(
			@Param("uid")String uid, @Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(CollectBean bean);
	public int update(CollectBean bean);
	public int deleteByCollectId(String collectId);
}
