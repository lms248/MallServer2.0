package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.CollectBean;
import bean.client.GoodsBean;
import bean.client.ShopBean;
import bean.client.UserBean;

import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;

import dao.mybatis.CollectDao;
import dao.mybatis.GoodsDao;
import dao.mybatis.ShopDao;
import dao.mybatis.UserDao;

/**
 * 商品收藏
 */
@Controller
@RequestMapping("/collect")
public class CollectService {
	
	@Autowired  
    private UserDao userDao;
	@Autowired  
	private ShopDao shopDao;
	@Autowired  
	private GoodsDao goodsDao;
	@Autowired  
    private CollectDao collectDao;
	
	/** 添加或取消收藏 */
	@RequestMapping(value ="edit",method=RequestMethod.POST)
	@ResponseBody
	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String goodsId = request.getParameter("goodsId");
		
		JSONObject obj = new JSONObject();
		
		UserBean user = userDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		GoodsBean goods = goodsDao.loadByGoodsId(goodsId);
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		CollectBean collect = collectDao.loadByUidAndGoodId(user.getUid(), goodsId);
		if (collect == null) { //不存在则添加
			String collectId = IdGen.get().nextId()+"";
			collect = new CollectBean();
			collect.setCollectId(collectId);
			collect.setUid(user.getUid());
			collect.setGoodsId(goodsId);
			collect.setCreateTime(System.currentTimeMillis());
			collectDao.save(collect);
			
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "添加收藏成功");
			obj.put("type", 1);
		} else { //已存在则删除
			collectDao.deleteByCollectId(collect.getCollectId());
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "取消收藏成功");
			obj.put("type", 0);
		}
		
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 收藏列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		
		JSONObject obj = new JSONObject();
		
		UserBean user = userDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		List<CollectBean> collectList = collectDao.loadByUid(user.getUid(), index, size);
		
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < collectList.size(); i++) {
			GoodsBean goods = goodsDao.loadByGoodsId(collectList.get(i).getGoodsId());
			if (goods == null) {
				continue;
			}
			ShopBean shop = shopDao.loadByShopId(goods.getShopId());
			if (shop == null) {
				continue;
			}
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(goods));
			obj2.put("shopId", goods.getShopId());
			obj2.put("goodsId", goods.getGoodsId());
			obj2.put("shopName", shop.getName());
			obj2.put("shopLogo", shop.getImage());
			obj2.put("shopThumb", shop.getThumbnail());
			obj2.put("contactPhone", shop.getContactPhone());
			arr.add(obj2);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商品列表");
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
