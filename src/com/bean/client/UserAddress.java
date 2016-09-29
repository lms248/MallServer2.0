package bean.client;


/**
 * 用户收货地址
 */
public class UserAddress{
	
	/** 地址ID */
	private String addressId;
	/** 联系人 */
	private String contact;
	/** 手机号 */
	private String phone;
	/** 所在地区 */
	private String area;
	/** 详细地址 */
	private String address;
	/** 是否默认地址 */
	//private boolean isDefault;
	
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	/*public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}*/
	
}
