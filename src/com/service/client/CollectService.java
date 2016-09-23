package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import dao.client.CollectDao;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import dao.client.UserDao;

/**
 * 商品收藏
 */
@Controller
@RequestMapping("/collect")
public class CollectService {
	
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
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		GoodsBean goods = GoodsDao.loadByGoodsId(Long.parseLong(goodsId));
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		CollectBean collect = CollectDao.loadByUidAndGoodId(user.getUid(), Long.parseLong(goodsId));
		if (collect == null) { //不存在则添加
			long collectId = IdGen.get().nextId();
			collect = new CollectBean();
			collect.setCollectId(collectId);
			collect.setUid(user.getUid());
			collect.setGoodsId(Long.parseLong(goodsId));
			collect.setCreateTime(System.currentTimeMillis());
			CollectDao.save(collect);
			
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "添加收藏成功");
			obj.put("data", JsonUtils.jsonFromObject(collect));
		} else { //已存在则删除
			CollectDao.deleteByCollectId(collect.getCollectId());
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "取消收藏成功");
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
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		List<CollectBean> collectList = CollectDao.loadByUid(user.getUid(), index, size);
		
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < collectList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(collectList.get(i).getGoodsId());
			if (goods == null) {
				continue;
			}
			ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
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
