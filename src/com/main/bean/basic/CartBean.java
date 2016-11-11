package main.bean.basic;

import common.db.Pojo;

/**
 * 购物车bean
 */
public class CartBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 购物车ID */
	private String cartId;
	/** 用户ID */
	private String uid;
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
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
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
