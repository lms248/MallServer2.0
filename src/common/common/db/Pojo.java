package common.db;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @Description 持久化基类
 */
public abstract class Pojo implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String tableName() {
		String tn=getClass().getSimpleName() ;
		if(tn.endsWith("Bean")) {
			tn=tn.substring(0,tn.length()-4);
		}
		tn = tn.toLowerCase();/*lims20150303添加*/
		return tn;
	}

	protected String cacheRegion() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * 列出要插入到数据库的字段集合，子类可以按照实际需求覆盖
	 * @return
	 */
	public Map<String, String> listInsertableFields() {
		try {
			Map<String, String> props = BeanUtils.describe(this);
			props.remove("id");
			props.remove("class");
			for(String s:props.keySet()){
				if(s.endsWith("_"))props.remove(s);
			}
			return props;
		} catch (Exception e) {
			throw new RuntimeException("Exception when Fetching fields of "
					+ this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		// 不同的子类尽管ID是相同也是不相等的
		if (!getClass().equals(obj.getClass()))
			return false;
		Pojo wb = (Pojo) obj;
		return wb.getId() == getId();
	}

}
