package main.dao.basic;

import main.bean.basic.CartBean;

import org.springframework.stereotype.Repository;

/**
 * 购物车dao
 */
@Repository
public interface CartDao {
	
	public CartBean loadById(int id);
	
	public CartBean loadByCartId(String cartId);
	
	public CartBean loadByUid(String uid);
	
	public int count();
	
	public int save(CartBean bean);
	public int update(CartBean bean);
	public int deleteByCartId(String cartId);
}
