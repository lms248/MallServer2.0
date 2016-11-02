package dao.basic;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.basic.CommunityBean;

/**
 * 社区dao
 */
@Repository
public interface CommunityDao {
	
	public CommunityBean loadById(int id);
	
	public CommunityBean loadByCommunityId(String communityId);
	
	public List<CommunityBean> loadAllCommunity(@Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(CommunityBean bean);
	public int update(CommunityBean bean);
	public int deleteByCommunityId(String communityId);
}
