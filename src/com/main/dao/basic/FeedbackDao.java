package main.dao.basic;

import java.util.List;

import main.bean.basic.FeedbackBean;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 反馈dao
 */
@Repository
public interface FeedbackDao {
	
	public FeedbackBean loadById(int id);
	
	public FeedbackBean loadByFeedbackId(String feedbackId);
	
	public List<FeedbackBean> loadAllFeedback(@Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(FeedbackBean bean);
	public int update(FeedbackBean bean);
	public int deleteByFeedbackId(String feedbackId);
}
