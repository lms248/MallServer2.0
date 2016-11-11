package main.bean.basic;

import common.db.Pojo;

/**
 * 反馈bean
 */
public class FeedbackBean extends Pojo {
	
	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 反馈ID */
	private String feedbackId;
	/** 用户ID */
	private String uid;
	/** 反馈信息 */
	private String info;
	/** 提交时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
