package main.bean.basic;

import common.db.Pojo;

/**
 * 商品收藏bean
 */
public class CollectBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 收藏ID */
	private String collectId;
	/** 用户ID */
	private String uid;
	/** 商品ID */
	private String goodsId;
	/** 创建时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
