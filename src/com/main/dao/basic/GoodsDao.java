package main.dao.basic;

import java.util.List;

import main.bean.basic.GoodsBean;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品dao
 */
@Repository
public interface GoodsDao {
	
	public GoodsBean loadById(int id);
	
	public GoodsBean loadByGoodsId(String goodsId);
	
	public List<GoodsBean> loadAllGoods(@Param("index")int index, @Param("size")int size);
	
	public List<GoodsBean> loadAllGoods_search(
			@Param("searchContent")String searchContent, @Param("index")int index, @Param("size")int size);
	
	public List<GoodsBean> loadAllGoodsBySort_search(
			@Param("sortId")int sortId, @Param("sortPid")int sortPid, 
			@Param("searchContent")String searchContent, @Param("index")int index, @Param("size")int size);
	
	public List<GoodsBean> loadAllGoodsByShop_search(
			@Param("shopId")String shopId, @Param("searchContent")String searchContent, 
			@Param("index")int index, @Param("size")int size);
	
	public List<GoodsBean> loadAllGoodsByShopAndSort_search(
			@Param("shopId")String shopId, @Param("sortId")int sortId, @Param("sortPid")int sortPid, 
			@Param("searchContent")String searchContent, @Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(GoodsBean bean);
	public int update(GoodsBean bean);
	public int deleteByGoodsId(String goodsId);
	
}
