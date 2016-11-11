package main.service.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.bean.basic.ActivityBean;
import main.bean.basic.GoodsBean;
import main.bean.basic.ShopBean;
import main.bean.basic.UserBean;
import main.dao.basic.ActivityDao;
import main.dao.basic.CollectDao;
import main.dao.basic.GoodsDao;
import main.dao.basic.ShopDao;
import main.dao.basic.SortDao;
import main.dao.basic.UserDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.utils.Def;
import common.utils.JsonUtils;

/**
 * 界面
 */
@Controller
@RequestMapping("/page")
public class PageService {
	
	@Autowired  
    private UserDao userDao;
	@Autowired  
	private ShopDao shopDao;
	@Autowired  
	private GoodsDao goodsDao;
	@Autowired  
    private SortDao sortDao;
	@Autowired  
	private ActivityDao activityDao;
	@Autowired  
    private CollectDao collectDao;
	
	/** 首页 */
	@RequestMapping(value ="home",method=RequestMethod.GET)
	@ResponseBody
	public void home(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		UserBean user = userDao.loadByToken(token);
		
		//首页banner
		JSONObject obj_banner = new JSONObject();
		JSONArray arr_banner = new JSONArray();
		List<ActivityBean> bannerList = activityDao.loadBySort(Def.ACTIVITY_BANNER, -1, 0, 5);
		for (int i = 0; i < bannerList.size(); i++) {
			GoodsBean goods = goodsDao.loadByGoodsId(bannerList.get(i).getGoodsId());
			
			obj_banner.put("title", bannerList.get(i).getTitle());
			obj_banner.put("goodsId", goods.getGoodsId());
			obj_banner.put("logo", goods.getLogo());
			obj_banner.put("logoThumb", goods.getLogoThumb());
			obj_banner.put("sortId", Def.ACTIVITY_BANNER);
			arr_banner.add(obj_banner);
		}
		
		//首页5大活动标签
		JSONObject obj_tags = new JSONObject();
		JSONArray arr_tags = new JSONArray();
		obj_tags.put("sortId", Def.ACTIVITY_DAJUHUI);
		obj_tags.put("data", sortDao.loadByPid(Def.ACTIVITY_DAJUHUI));//大聚惠
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_HAIWAIGOU);
		obj_tags.put("data", sortDao.loadByPid(Def.ACTIVITY_HAIWAIGOU));//海外购
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_CHAOSHIBAIHUO);
		obj_tags.put("data", sortDao.loadByPid(Def.ACTIVITY_CHAOSHIBAIHUO));//超市百货
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_CHANGJIAZHIXIAO);
		obj_tags.put("data", sortDao.loadByPid(Def.ACTIVITY_CHANGJIAZHIXIAO));//厂家直销
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_MEISHIYULE);
		obj_tags.put("data", sortDao.loadByPid(Def.ACTIVITY_MEISHIYULE));//美食娱乐
		arr_tags.add(obj_tags);
		
		
		//首页精选促销
		JSONObject obj_promotion = new JSONObject();
		JSONArray arr_promotion = new JSONArray();
		
		List<ActivityBean> promotionList = activityDao.loadBySort(Def.ACTIVITY_PROMOTION, -1, 0, 10);
		for (int i = 0; i < promotionList.size(); i++) {
			GoodsBean goods = goodsDao.loadByGoodsId(promotionList.get(i).getGoodsId());
			ShopBean shop = shopDao.loadByShopId(goods.getShopId());
			if (shop == null) {
				continue;
			}
			
			int isCollect = 0;//是否已收藏，0否，1是
			if (user != null) {
				if (collectDao.loadByUidAndGoodId(user.getUid(), goods.getGoodsId()) != null) {
					isCollect = 1;
				}
			}
			
			obj_promotion = JSONObject.fromObject(JsonUtils.jsonFromObject(goods));
			obj_banner.put("title", promotionList.get(i).getTitle());
			obj_promotion.put("shopId", goods.getShopId());
			obj_promotion.put("shopName", shop.getName());
			obj_promotion.put("shopLogo", shop.getImage());
			obj_promotion.put("shopThumb", shop.getThumbnail());
			obj_promotion.put("contactPhone", shop.getContactPhone());
			obj_promotion.put("isCollect", isCollect);
			arr_promotion.add(obj_promotion);
		}
		
		//首页商城商户
		//JSONObject obj_shop = new JSONObject();
		JSONArray arr_shop = new JSONArray();
		List<ShopBean> shopList = shopDao.loadAllShop(0, 4);
		for (int i = 0; i < shopList.size(); i++) {
			arr_shop.add(JsonUtils.jsonFromObject(shopList.get(i)));
		}
		
		JSONObject obj_data = new JSONObject();
		obj_data.put("banner", arr_banner);
		obj_data.put("tags", arr_tags);
		obj_data.put("promotion", arr_promotion);
		obj_data.put("shop", arr_shop);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "首页数据");
		obj.put("data", obj_data);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 社区 */
	@Deprecated
	@RequestMapping(value ="community",method=RequestMethod.GET)
	@ResponseBody
	public void community(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "社区数据");
		obj.put("data", "");
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 购物车 */
	@Deprecated
	@RequestMapping(value ="shoppingCart",method=RequestMethod.GET)
	@ResponseBody
	public void shoppingCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车数据");
		obj.put("data", JsonUtils.jsonFromObject(goodsDao.loadAllGoods(1, 10)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 我（个人中心） */
	@Deprecated
	@RequestMapping(value ="me",method=RequestMethod.GET)
	@ResponseBody
	public void me(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "个人数据");
		obj.put("data", "");
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 应用信息 */
	@RequestMapping(value ="appInfo",method=RequestMethod.GET)
	@ResponseBody
	public void appInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		JSONObject outObj = new JSONObject();
		outObj.put("contactPhone", "13800000000");
		outObj.put("aboutUs", "这里是关于我们的内容");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "应用信息");
		obj.put("data", outObj);
		out.print(obj);
		
		out.flush();
		out.close();
	}
}
