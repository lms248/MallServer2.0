package dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import bean.client.UserBean;

@Repository
public interface UserDao {
	
	public UserBean loadById(int id); 
	
	public UserBean loadByUid(String uid); 
	
	public UserBean loadByUsername(String username); 
	
	public UserBean loadByToken(String token); 
	
	public List<UserBean> loadAllUser(int index, int size); 
	
	public int save(UserBean bean);
	public int update(UserBean bean);
}
