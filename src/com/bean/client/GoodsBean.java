package bean.client;

import common.db.Pojo;

/**
 * 商品bean
 * */
public class GoodsBean extends Pojo {
	private static final long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 商品ID */
	private long goodsId;
	/** 商店ID */
	private long shopId;
	/** 商品名 */
	private String name;
	/** 标题 */
	private String title;
	/** 缩略图 */
	private String thumbnail;
	/** 图片列表 */
	private String imageList;
	/** 当前价格 */
	private String curPrice;
	/** 原来价格 */
	private String prePrice;
	/** 颜色 */
	private String color;
	/** 尺寸 */
	private String size;
	/** 介绍 */
	private String desc;
	/** 类型 */
	private String type;
	/** 状态 */
	private String status;
	/** 添加时间 */
	private long time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getImageList() {
		return imageList;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public String getCurPrice() {
		return curPrice;
	}
	public void setCurPrice(String curPrice) {
		this.curPrice = curPrice;
	}
	public String getPrePrice() {
		return prePrice;
	}
	public void setPrePrice(String prePrice) {
		this.prePrice = prePrice;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
