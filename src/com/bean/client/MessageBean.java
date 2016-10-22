package bean.client;

import common.db.Pojo;

/**
 * 消息bean
 */
public class MessageBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 消息ID */
	private String messageId;
	/** 用户ID */
	private String uid;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	/** 创建时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
