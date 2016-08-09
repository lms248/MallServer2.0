package bean.client;

import common.db.Pojo;

/**
 * 社区bean
 * */
public class CommunityBean extends Pojo {
	private static final long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 社区ID */
	private long communityId;
	/** 用户ID */
	private long uid;
	/** 描述 */
	private String desc;
	/** 图片列表 */
	private String imageList;
	/** 发布时间 */
	private long time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(long communityId) {
		this.communityId = communityId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImageList() {
		return imageList;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
