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
	private String goodsId;
	/** 商店ID */
	private String shopId;
	/** 商品名 */
	private String name;
	/** 标题 */
	private String title;
	/** 商品Logo */
	private String logo;
	/** Logo缩略图 */
	private String logoThumb;
	/** 图片列表 */
	private String imageList;
	/** 缩略图列表 */
	private String thumbList;
	/** 当前价格 */
	private double curPrice;
	/** 原来价格 */
	private double prePrice;
	/** 属性标记 */
	private String tags;
	/** 介绍 */
	private String details;
	/** 分类ID */
	private int sortId;
	/** 库存 */
	private int stock;
	/** 类型 */
	private String type;
	/** 状态 */
	private String status;
	/** 创建时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLogoThumb() {
		return logoThumb;
	}
	public void setLogoThumb(String logoThumb) {
		this.logoThumb = logoThumb;
	}
	public String getImageList() {
		return imageList;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public String getThumbList() {
		return thumbList;
	}
	public void setThumbList(String thumbList) {
		this.thumbList = thumbList;
	}
	public double getCurPrice() {
		return curPrice;
	}
	public void setCurPrice(double curPrice) {
		this.curPrice = curPrice;
	}
	public double getPrePrice() {
		return prePrice;
	}
	public void setPrePrice(double prePrice) {
		this.prePrice = prePrice;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
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
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
