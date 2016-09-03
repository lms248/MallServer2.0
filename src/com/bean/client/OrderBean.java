package bean.client;

import common.db.Pojo;

/**
 * 订单bean
 * */
public class OrderBean extends Pojo {
	private static final long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 订单ID */
	private long ordeId;
	/** 用户ID */
	private String uid;
	/** 商品ID */
	private String goodsId;
	/** 数量 */
	private String count;
	/** 颜色 */
	private String color;
	/** 尺寸 */
	private String size;
	/** 下单时间 */
	private long time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getOrdeId() {
		return ordeId;
	}
	public void setOrdeId(long ordeId) {
		this.ordeId = ordeId;
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
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
