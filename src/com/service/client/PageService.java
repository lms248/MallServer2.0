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

import bean.client.GoodsBean;
import bean.client.GoodssortBean;
import bean.client.ShopBean;
import common.utils.Def;
import common.utils.JsonUtils;
import dao.client.GoodsDao;
import dao.client.GoodssortDao;
import dao.client.ShopDao;

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
		List<GoodssortBean> goodssortList = GoodssortDao.loadByLevel_1(Def.GOODS_TYPE_BANNER, 0, 5);
		for (int i = 0; i < goodssortList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(goodssortList.get(i).getGoodsId());
			obj_banner.put("goodsId", goods.getGoodsId());
			obj_banner.put("logo", goods.getLogo());
			obj_banner.put("logoThumb", goods.getLogoThumb());
			//obj_banner.put("data", JsonUtils.jsonFromObject(GoodsDao.loadByGoodsId(goodssortList.get(i).getGoodsId())));
			arr_banner.add(obj_banner);
		}
		
		//首页精选促销
		JSONObject obj_promotion = new JSONObject();
		JSONArray arr_promotion = new JSONArray();
		List<GoodssortBean> promotionList = GoodssortDao.loadByLevel_1(Def.GOODS_TYPE_BANNER, 0, 10);
		for (int i = 0; i < promotionList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(promotionList.get(i).getGoodsId());
			/*obj_promotion.put("goodsId", goods.getGoodsId());
			obj_promotion.put("name", goods.getName());
			obj_promotion.put("logo", goods.getLogo());
			obj_promotion.put("logoThumb", goods.getLogoThumb());
			obj_promotion.put("prePrice", goods.getPrePrice());
			obj_promotion.put("curPrice", goods.getCurPrice());
			obj_promotion.put("logoThumb", goods.getLogoThumb());*/
			//obj_promotion.put("data", JsonUtils.jsonFromObject(promotionList.get(i)));
			arr_promotion.add(JsonUtils.jsonFromObject(goods));
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
