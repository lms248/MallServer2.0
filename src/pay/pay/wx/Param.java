package pay.wx;

import java.text.MessageFormat;

import com.cmge.pay.platform.common.exception.PPFException;
import com.cmge.pay.platform.common.repository.entity.interfaces.Validatable;
import com.cmge.pay.platform.common.util.JsonUtil;

public class Param implements Comparable<Param>, Validatable {

	private String name;

	private String value;

	private boolean sign;

	public String info() {
		return name + "=" + value;
	}

	public String xml() {
		return MessageFormat.format("<{0}>{1}</{0}>", name, value);
	}

	@Override
	public int compareTo(Param o) {
		int i = name.compareTo(o.name);
		if (i != 0) {
			return i;
		}
		return value.compareTo(o.value);
	}

	@Override
	public void validateAndFix() {
		if (JsonUtil.isNullOrEmpty(name)) {
			throw new PPFException("参数名不能为空！");
		}
		if (JsonUtil.isNullOrEmpty(value)) {
			throw new PPFException("参数值不能为空！");
		}
		name = name.trim();
		value = value.trim();
	}

	public static Param valueOf(String name, String value, boolean sign) {
		Param param = new Param();
		param.name = name;
		param.value = value;
		param.sign = sign;
		return param;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

}
