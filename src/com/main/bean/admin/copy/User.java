package main.bean.admin.copy;

import main.service.admin.AdminUserService;
import common.utils.StringUtils;

/**
 * 用户信息实体
 */
public class User {
	private int id;
	private int groupid;
	private String name;
	private String password;
	private String auth;
	
	/**权限数组*/
	private int[] authArray;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int[] authArray() {
		return authArray;
	}
	public void initAuthArray(){
		if(StringUtils.isNotBlank(this.auth)){
			if("all".equalsIgnoreCase(this.auth.trim())){
				this.authArray=new int[AdminUserService.authList.size()];
				for(int i=0;i<AdminUserService.authList.size();i++){
					this.authArray[i]=AdminUserService.authList.get(i).getAuthCode();
				}
			}else{
				String[] as = this.auth.split(",");
				this.authArray=new int[as.length];
				for(int i=0;i<as.length;i++){
					this.authArray[i]=Integer.valueOf(as[i]);
				}
			}
		}
	}
	public boolean hasAuth(int code){
		if(this.authArray!=null&&this.authArray.length>0)
			for(int i:this.authArray){
				if(i==code)return true;
			}
		return false;
	}
}
