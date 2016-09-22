package bean.client;

import common.db.Pojo;

/**
 * 商品收藏bean
 */
public class CollectBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 收藏ID */
	private long collectId;
	/** 用户ID */
	private long uid;
	/** 商品ID */
	private long goodsId;
	/** 创建时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCollectId() {
		return collectId;
	}
	public void setCollectId(long collectId) {
		this.collectId = collectId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
