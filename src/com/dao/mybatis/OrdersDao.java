package dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.client.OrdersBean;

/**
 * 支付dao
 */
@Repository
public interface OrdersDao {
	
	public OrdersBean loadById(int id);
	
	public OrdersBean loadByOrderId(String orderId);
	
	public List<OrdersBean> loadByUidAndStatus(@Param("uid")String uid, @Param("status")int status);
	
	public List<OrdersBean> loadByStatus(
			@Param("status")int status, @Param("index")int index, @Param("size")int size);
	
	public List<OrdersBean> loadAllOrder(@Param("index")int index, @Param("size")int size);
	
	public int count();
	public int countByStatus(int status);
	
	public int save(OrdersBean bean);
	public int update(OrdersBean bean);
	public int deleteByOrderId(String orderId);
}
