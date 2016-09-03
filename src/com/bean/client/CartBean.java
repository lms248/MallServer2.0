package bean.client;

import common.db.Pojo;

/**
 * 购物车bean
 */
public class CartBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 购物车ID */
	private long cartId;
	/** 用户ID */
	private long uid;
	/** 
	 * 商品列表信息JSONArray，[{"goodsId":xxxx,"amount":xx,"tags":[{"tagName1":"xxx"},{"tagName2":"xxx"}]}]
	 */
	private String goodsList;
	/** 更新时间 */
	private long updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(String goodsList) {
		this.goodsList = goodsList;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
