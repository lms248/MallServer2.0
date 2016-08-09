package bean.client;

import common.db.Pojo;

/**
 * 商店bean
 * */
public class ShopBean extends Pojo {
	private static final long serialVersionUID = 1L;
	/** 序号ID */
	private int id;
	/** 商店ID */
	private long shopId;
	/** 商店名 */
	private String name;
	/** 标题 */
	private String title;
	/** 缩略图 */
	private String thumbnail;
	/** 图片 */
	private String image;
	/** 类型 */
	private String type;
	/** 注册时间 */
	private long time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
