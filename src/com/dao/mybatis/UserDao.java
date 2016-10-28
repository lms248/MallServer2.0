package dao.mybatis;

import java.util.List;

import bean.client.UserBean;

public interface UserDao {
	
	//public List<UserBean> loadById(int id) throws Exception; 
	public UserBean loadById(int id); 
	
	public List<UserBean> loadAllUser(int index, int size); 
	
}
