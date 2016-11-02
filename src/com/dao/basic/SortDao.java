package dao.basic;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import bean.basic.SortBean;

/**
 * 商品分类dao
 */
@Repository
public interface SortDao {
	
	public SortBean loadById(int id);
	
	public List<SortBean> loadByPid(int pid);
	
	public List<SortBean> loadByPidAndType(@Param("pid")int pid, @Param("type")int type);
	
	public List<SortBean> loadAllSort(@Param("index")int index, @Param("size")int size);
	
	public int count();
	
	public int save(SortBean bean);
	public int update(SortBean bean);
	public int deleteById(int id);
}
