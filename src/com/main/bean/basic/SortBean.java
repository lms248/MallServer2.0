package main.bean.basic;

import common.db.Pojo;

/**
 * 商品分类bean
 */
public class SortBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	/** 分类ID */
	private int id;
	/** 父类型ID（对应id） */
	private int pid;
	/** 名字 */
	private String name;
	/** 类型 */
	private int type;
	/** 分类级别 */
	private int level;
	/** 备注 */
	private String mark;
	/** 分类Logo */
	private String logo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}
