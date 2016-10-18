package pay.common;

/**
 * 支付方式
 */
public enum PayWay {
	ALIPAY("支付宝", 1), WECHAT("微信", 2);
	 
	private String name;
	private int index;
	 
	private PayWay(String name, int index) {
		this.name = name;
		this.index = index;
	}
	 
	public static String getName(int index) {
		for (PayWay p : PayWay.values()) {
			if (p.getIndex() == index) {
				return p.getName();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
