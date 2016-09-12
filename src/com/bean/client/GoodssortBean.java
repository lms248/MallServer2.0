package bean.client;

import common.db.Pojo;

/**
 * 商品分类
 */
public class GoodssortBean extends Pojo {

	private static final long serialVersionUID = 1L;
	
	private int id;
	/** 商品分类ID */
	private long goodssortId;
	/** 类别一 */
	private int level_1;
	/** 类别二 */
	private int level_2;
	/** 类别三 */
	private int level_3;
	/** 商品ID */
	private long goodsId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getGoodssortId() {
		return goodssortId;
	}
	public void setGoodssortId(long goodssortId) {
		this.goodssortId = goodssortId;
	}
	public int getLevel_1() {
		return level_1;
	}
	public void setLevel_1(int level_1) {
		this.level_1 = level_1;
	}
	public int getLevel_2() {
		return level_2;
	}
	public void setLevel_2(int level_2) {
		this.level_2 = level_2;
	}
	public int getLevel_3() {
		return level_3;
	}
	public void setLevel_3(int level_3) {
		this.level_3 = level_3;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	
}
