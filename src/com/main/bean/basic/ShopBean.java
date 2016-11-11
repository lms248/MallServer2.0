package main.bean.basic;

import common.db.Pojo;

/**
 * 商店bean
 * */
public class ShopBean extends Pojo {
	private static final long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 商店ID */
	private String shopId;
	/** 商店名 */
	private String name;
	/** 标题 */
	private String title;
	/** 内容介绍 */
	private String details;
	/** 店铺Logo */
	private String logo;
	/** 店铺Logo缩略图 */
	private String logoThumb;
	/** 图片 */
	private String image;
	/** 缩略图 */
	private String thumbnail;
	/** 联系客服电话 */
	private String contactPhone;
	/** 类型 */
	private String type;
	/** 创建时间 */
	private long createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getThumbnail() {
		return thumbnail;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
