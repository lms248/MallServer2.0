package dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.client.ActivityBean;

/**
 * 活动dao
 */
@Repository
public interface ActivityDao {
	
	public ActivityBean loadById(int id);
	
	public ActivityBean loadByActivityId(String activityId);
	
	public List<ActivityBean> loadBySort(
			@Param("sortId")int sortId, @Param("sortPid")int sortPid, @Param("index")int index, @Param("size")int size);
	
	public List<ActivityBean> loadAllActivity(@Param("index")int index, @Param("size")int size);
	
	public List<ActivityBean> loadAllActivity_search(
			@Param("searchContent")String searchContent, @Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(ActivityBean bean);
	public int update(ActivityBean bean);
	public int deleteByActivityId(String activityId);
}
