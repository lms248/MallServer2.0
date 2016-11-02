package dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.client.UserBean;

/**
 * 用户dao
 */
@Repository
public interface UserDao {
	
	public UserBean loadById(int id); 
	
	public UserBean loadByUid(String uid); 
	
	public UserBean loadByUsername(String username); 
	
	public UserBean loadByToken(String token); 
	
	public UserBean loadByUidAndType(@Param("uid")String uid, @Param("type")int type); 
	
	public List<UserBean> loadAllUser(@Param("index")int index, @Param("size")int size); 
	
	public List<UserBean> loadAllUser_search(
			@Param("searchContent")String searchContent, @Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(UserBean bean);
	public int update(UserBean bean);
	public int deleteByUid(String uid);
}
