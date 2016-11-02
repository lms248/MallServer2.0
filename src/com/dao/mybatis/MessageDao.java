package dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.client.MessageBean;

/**
 * 消息dao
 */
@Repository
public interface MessageDao {
	
	public MessageBean loadById(int id);
	
	public MessageBean loadByMessageId(String messageId);
	
	public List<MessageBean> loadByUid(@Param("uid")String uid, @Param("index")int index, @Param("size")int size);
	
	public List<MessageBean> loadAllMessage(@Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(MessageBean bean);
	public int update(MessageBean bean);
	public int deleteByMessageId(String messageId);
}
