package bean.client;

import common.db.Pojo;

/**
 * 商店bean
 * */
public class ShopBean extends Pojo {
	private static long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 商店ID */
	private Long shopid;
	/** 商店名 */
	private String name;
	/** 标题 */
	private String title;
	/** 缩略图 */
	private String thumbnail;
	/** 图片 */
	private String image;
	/** 注册时间 */
	private Long time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
