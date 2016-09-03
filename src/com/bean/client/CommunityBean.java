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
	/** 内容详情 */
	private String details;
	/** 图片列表 */
	private String imageList;
	/** 缩略图片列表 */
	private String thumbList;
	/** 创建时间 */
	private long createTime;
	
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getImageList() {
		return imageList;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public String getThumbList() {
		return thumbList;
	}
	public void setThumbList(String thumbList) {
		this.thumbList = thumbList;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
