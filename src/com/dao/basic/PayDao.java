package dao.basic;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.basic.PayBean;

/**
 * 订单dao
 */
@Repository
public interface PayDao {
	
	public PayBean loadById(int id);
	
	public PayBean loadByPayId(String payId);
	
	public List<PayBean> loadByStatus(
			@Param("status")int status, @Param("index")int index, @Param("size")int size);
	
	public List<PayBean> loadAllPay(@Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(PayBean bean);
	public int update(PayBean bean);
	public int deleteByPayId(String payId);
}
