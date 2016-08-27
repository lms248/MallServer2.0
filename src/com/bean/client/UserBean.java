package bean.client;

import common.db.Pojo;

/**
 * 用户bean
 * */
public class UserBean extends Pojo {
	private static final long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 用户ID */
	private long uid;
	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	/** 手机号 */
	private String phone;
	/** 昵称 */
	private String nickname;
	/** 头像 */
	private String avatar;
	/** 缩略图 */
	private String thumbnail;
	/** 令牌*/
	private String token;
	/** 最近登录时间*/
	private long loginTime;
	/** 注册时间 */
	private long registerTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
