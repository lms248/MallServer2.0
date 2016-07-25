package bean.client;

import common.db.Pojo;

/**
 * 商品bean
 * */
public class GoodsBean extends Pojo {
	private static long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 商品ID */
	private Long goodsid;
	/** 商店ID */
	private Long shopid;
	/** 商品名 */
	private String name;
	/** 标题 */
	private String title;
	/** 缩略图 */
	private String thumbnail;
	/** 图片列表 */
	private String imagelist;
	/** 当前价格 */
	private String cur_price;
	/** 原来价格 */
	private String pre_price;
	/** 颜色 */
	private String color;
	/** 尺寸 */
	private String size;
	/** 介绍 */
	private String introduction;
	/** 类型 */
	private String type;
	/** 状态 */
	private String status;
	/** 添加时间 */
	private Long time;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}
	public Long getShopid() {
		return shopid;
	}
	public void setShopid(Long shopid) {
		this.shopid = shopid;
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
	public String getImagelist() {
		return imagelist;
	}
	public void setImagelist(String imagelist) {
		this.imagelist = imagelist;
	}
	public String getCur_price() {
		return cur_price;
	}
	public void setCur_price(String cur_price) {
		this.cur_price = cur_price;
	}
	public String getPre_price() {
		return pre_price;
	}
	public void setPre_price(String pre_price) {
		this.pre_price = pre_price;
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
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
}
