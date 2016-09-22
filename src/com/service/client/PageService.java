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

import bean.client.ActivityBean;
import bean.client.GoodsBean;
import bean.client.ShopBean;

import common.utils.Def;
import common.utils.JsonUtils;

import dao.client.ActivityDao;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import dao.client.SortDao;

/**
 * 界面
 */
@Controller
@RequestMapping("/page")
public class PageService {
	/** 首页 */
	@RequestMapping(value ="home",method=RequestMethod.GET)
	@ResponseBody
	public void home(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//首页banner
		JSONObject obj_banner = new JSONObject();
		JSONArray arr_banner = new JSONArray();
		/*List<GoodssortBean> goodssortList = GoodssortDao.loadByLevel_1(Def.ACTIVITY_BANNER, 0, 5);
		for (int i = 0; i < goodssortList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(goodssortList.get(i).getGoodsId());
			obj_banner.put("goodsId", goods.getGoodsId());
			obj_banner.put("logo", goods.getLogo());
			obj_banner.put("logoThumb", goods.getLogoThumb());
			obj_banner.put("sortId", Def.ACTIVITY_BANNER);
			//obj_banner.put("data", JsonUtils.jsonFromObject(GoodsDao.loadByGoodsId(goodssortList.get(i).getGoodsId())));
			arr_banner.add(obj_banner);
		}*/
		List<ActivityBean> bannerList = ActivityDao.loadActivityForSort(Def.ACTIVITY_BANNER, -1, 0, 5);
		for (int i = 0; i < bannerList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(bannerList.get(i).getGoodsId());
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
		obj_tags.put("data", SortDao.loadByPid(Def.ACTIVITY_DAJUHUI));//大聚惠
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_HAIWAIGOU);
		obj_tags.put("data", SortDao.loadByPid(Def.ACTIVITY_HAIWAIGOU));//海外购
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_CHAOSHIBAIHUO);
		obj_tags.put("data", SortDao.loadByPid(Def.ACTIVITY_CHAOSHIBAIHUO));//超市百货
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_CHANGJIAZHIXIAO);
		obj_tags.put("data", SortDao.loadByPid(Def.ACTIVITY_CHANGJIAZHIXIAO));//厂家直销
		arr_tags.add(obj_tags);
		obj_tags.put("sortId", Def.ACTIVITY_MEISHIYULE);
		obj_tags.put("data", SortDao.loadByPid(Def.ACTIVITY_MEISHIYULE));//美食娱乐
		arr_tags.add(obj_tags);
		
		
		//首页精选促销
		JSONObject obj_promotion = new JSONObject();
		JSONArray arr_promotion = new JSONArray();
		/*List<GoodssortBean> promotionList = GoodssortDao.loadByLevel_1(Def.ACTIVITY_PROMOTION, 0, 10);
		for (int i = 0; i < promotionList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(promotionList.get(i).getGoodsId());
			ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
			if (shop == null) {
				continue;
			}
			obj_promotion = JSONObject.fromObject(JsonUtils.jsonFromObject(goods));
			obj_promotion.put("shopId", goods.getShopId());
			obj_promotion.put("shopName", shop.getName());
			obj_promotion.put("shopLogo", shop.getImage());
			obj_promotion.put("shopThumb", shop.getThumbnail());
			obj_promotion.put("contactPhone", shop.getContactPhone());
			arr_promotion.add(obj_promotion);
		}*/
		
		List<ActivityBean> promotionList = ActivityDao.loadActivityForSort(Def.ACTIVITY_PROMOTION, -1, 0, 10);
		for (int i = 0; i < promotionList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(promotionList.get(i).getGoodsId());
			ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
			if (shop == null) {
				continue;
			}
			obj_promotion = JSONObject.fromObject(JsonUtils.jsonFromObject(goods));
			obj_banner.put("title", promotionList.get(i).getTitle());
			obj_promotion.put("shopId", goods.getShopId());
			obj_promotion.put("shopName", shop.getName());
			obj_promotion.put("shopLogo", shop.getImage());
			obj_promotion.put("shopThumb", shop.getThumbnail());
			obj_promotion.put("contactPhone", shop.getContactPhone());
			arr_promotion.add(obj_promotion);
		}
		
		//首页商城商户
		JSONObject obj_shop = new JSONObject();
		JSONArray arr_shop = new JSONArray();
		List<ShopBean> shopList = ShopDao.loadAllShop(0, 4);
		for (int i = 0; i < shopList.size(); i++) {
			/*obj_shop.put("shopId", shopList.get(i).getShopId());
			obj_shop.put("name", shopList.get(i).getName());
			obj_shop.put("logo", shopList.get(i).getLogo());
			obj_shop.put("logoThumb", shopList.get(i).getLogoThumb());*/
			//obj_shop.put("data", JsonUtils.jsonFromObject(shopList.get(i)));
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
		obj.put("data", JsonUtils.jsonFromObject(GoodsDao.loadAllGoods(1, 10)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 我（个人中心） */
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
	
}
