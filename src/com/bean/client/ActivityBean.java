package bean.client;

import common.db.Pojo;

public class ActivityBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 活动ID */
	private long activityId;
	/** 商品ID */
	private long goodsId;
	/** 分类ID */
	private int sortId;
	/** 备注 */
	private String mark;
	/** 创建时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
