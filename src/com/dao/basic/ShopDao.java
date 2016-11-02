package dao.basic;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.basic.ShopBean;

/**
 * 支付dao
 */
@Repository
public interface ShopDao {
	
	public ShopBean loadById(int id);
	
	public ShopBean loadByShopId(String shopId);
	
	public ShopBean loadByShopname(String name);
	
	public List<ShopBean> loadAllShop(@Param("index")int index, @Param("size")int size);
	
	public List<ShopBean> loadAllShop_search(
			@Param("searchContent")String searchContent, @Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(ShopBean bean);
	public int update(ShopBean bean);
	public int deleteByShopId(String shopId);
}
