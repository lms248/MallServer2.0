package bean.client;

import common.db.Pojo;

/**
 * 支付bean
 */
public class PayBean extends Pojo {
	
	private static final long serialVersionUID = 1L;
	
	/** 序号ID */
	private int id;
	/** 用户ID */
	private String uid;
	/** 支付ID */
	private String payId;
	/** 支付方式 */
	private String payWay;
	/** 总金额(分) */
	private int total_fee;
	/** 货币种类,默认人民币：CNY */
	private String fee_type;
	/** 商品描述 */
	private String body;
	/** 支付订单号 */
	private String trade_no;
	/** 业务结果,SUCCESS/FAIL */
	private String result_code;
	/** 错误代码 */
	private String err_code;
	/** 错误代码描述 */
	private String err_code_des;
	/** 支付状态 */
	private int status;
	/** 创建时间 */
	private long createTime;
	/** 支付时间 */
	private long payTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getPayTime() {
		return payTime;
	}
	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}
	
}
