package main.bean.basic;

/**
 * 订单信息
 * */
public class OrderGoodsInfo {
	
	/** 商品ID */
	private String goodsId;
	/** 数量 */
	private int amount;
	/** 属性标签 */
	private String tags;
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
