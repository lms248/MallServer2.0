package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.CartBean;
import bean.client.GoodsBean;
import bean.client.ShopBean;
import bean.client.UserBean;

import com.alibaba.fastjson.JSON;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;

import dao.client.CartDao;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import dao.client.UserDao;

/**
 * 购物车
 */
@Controller
@RequestMapping("/cart")
public class CartService {
	
	/** 添加购物车 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String goodsId = request.getParameter("goodsId");
		String amount = request.getParameter("amount");
		String tags = request.getParameter("tags");
		
		System.out.println("tags===="+tags);
		System.out.println("JSON.parseObject(tags)===="+JSON.parseObject(tags));
		
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
		
		JSONObject cartObj = new JSONObject();
		CartBean cart = CartDao.loadByUid(user.getUid());
		long cartId = 0;
		if (cart == null) {
			cart = new CartBean();
			cartId = IdGen.get().nextId();
			cart.setCartId(cartId);
			cart.setUid(user.getUid());
			cartObj.put("goodsId", Long.parseLong(goodsId));
			cartObj.put("amount", Integer.parseInt(amount));
			cartObj.put("tags", JSON.parseObject(tags));
			JSONArray goodsList = new JSONArray();
			goodsList.add(cartObj);
			
			cart.setGoodsList(goodsList.toString());
			cart.setUpdateTime(System.currentTimeMillis());
			CartDao.save(cart);
		} else {
			cartId = cart.getCartId();
			cartObj.put("goodsId", Long.parseLong(goodsId));
			cartObj.put("amount", Integer.parseInt(amount));
			cartObj.put("tags", JSON.parseObject(tags));
			JSONArray goodsList = JSONArray.fromObject(cart.getGoodsList());
			JSONArray goodsList2 = new JSONArray();
			boolean isHave = false;
			for (int i = 0; i < goodsList.size(); i++) {
				JSONObject obj2 = JSONObject.fromObject(goodsList.get(i));
				System.out.println("tags===="+tags);
				System.out.println("JSON.parseObject(tags)===="+JSON.parseObject(tags));
				System.out.println("obj2.get('tags').toString()===="+obj2.get("tags").toString());
				if (obj2.get("goodsId") != null && goodsId.equals(obj2.get("goodsId").toString())  && JSON.parseObject(tags).toString().equals(obj2.get("tags").toString())) {
					obj2.put("amount", (int)obj2.get("amount")+Integer.parseInt(amount));
					isHave = true;
				}
				goodsList2.add(obj2);
			}
			if (!isHave) {
				goodsList2.add(cartObj);
			}
			
			cart.setGoodsList(goodsList2.toString());
			cart.setUpdateTime(System.currentTimeMillis());
			CartDao.update(cart);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加购物车成功");
		obj.put("data", JsonUtils.jsonFromObject(CartDao.loadByCartId(cartId)));
		out.print(obj);
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 购物车商品信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		int cartId = Integer.parseInt(request.getParameter("cartId")); 
		
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
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车信息");
		obj.put("data", JsonUtils.jsonFromObject(CartDao.loadByCartId(cartId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 购物车列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		
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
		
		CartBean cart = CartDao.loadByUid(user.getUid());
		
		if (cart == null || cart.getGoodsList() == null) {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "购物车列表");
			obj.put("data", null);
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		JSONArray arrOut = getCartlist(cart.getGoodsList());
		
		/*for (int i = 0; i < goodsList.size(); i++) {
			JSONObject obj3 = JSONObject.fromObject(goodsList.get(i));
			GoodsBean goods = GoodsDao.loadByGoodsId(obj3.getLong("goodsId"));
			ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
			obj2.put("shopId", shop.getShopId());
			obj2.put("shopName", shop.getName());
			obj2.put("shopImage", shop.getImage());
			obj2.put("shopThumb", shop.getThumbnail());
			obj2.put("goodsId", obj3.get("goodsId"));
			obj2.put("goodsName", goods.getName());
			obj2.put("goodsLogo", goods.getLogo());
			obj2.put("goodsLogoThumb", goods.getLogoThumb());
			obj2.put("prePrice", goods.getPrePrice());
			obj2.put("curPrice", goods.getCurPrice());
			obj2.put("amount", obj3.get("amount"));
			obj2.put("tags", obj3.get("tags"));
			arr.add(obj2);
		}*/
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车列表");
		obj.put("data", arrOut);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 删除 */
	@RequestMapping(value ="delete",method=RequestMethod.POST)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("------------/cart/delete-------------");
		
		String token = request.getParameter("token");
		String goodsId = request.getParameter("goodsId");
		String tags = request.getParameter("tags");
		
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
		
		CartBean cart = CartDao.loadByUid(user.getUid());
		if (cart == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "购物车为空");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		} else {
			JSONArray goodsList_all = JSONArray.fromObject(cart.getGoodsList());
			JSONArray goodsList_temp = new JSONArray();
			for (int i = 0; i < goodsList_all.size(); i++) {
				JSONObject obj2 = JSONObject.fromObject(goodsList_all.get(i));
				System.out.println("tags===="+tags);
				System.out.println("JSON.parseObject(tags)===="+JSON.parseObject(tags));
				System.out.println("obj2.get('tags').toString()===="+obj2.get("tags").toString());
				if (obj2.get("goodsId") != null 
						&& goodsId.equals(obj2.get("goodsId").toString())  
						&& JSON.parseObject(tags).toString().equals(obj2.get("tags").toString())) {
					continue;
				}
				goodsList_temp.add(obj2);
			}
			
			cart.setGoodsList(goodsList_temp.toString());
			cart.setUpdateTime(System.currentTimeMillis());
			CartDao.update(cart);
		}
		
		JSONArray arrOut = getCartlist(cart.getGoodsList());
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车列表");
		obj.put("data", arrOut);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 批量删除 */
	@RequestMapping(value ="deleteList",method=RequestMethod.POST)
	@ResponseBody
	public void deleteList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("------------/cart/deleteList-------------");
		
		String token = request.getParameter("token");
		String goodsList = request.getParameter("goodsList");
//		String goodsId = request.getParameter("goodsId");
//		String tags = request.getParameter("tags");
		
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
		
		CartBean cart = CartDao.loadByUid(user.getUid());
		if (cart == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "购物车为空");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		} else {
			JSONArray goodsList_all = JSONArray.fromObject(cart.getGoodsList());
			JSONArray goodsList_del = JSONArray.fromObject(goodsList);
			JSONArray goodsList_temp = goodsList_all;
			for (int i = 0; i < goodsList_del.size(); i++) {
				goodsList_temp = new JSONArray();
				JSONObject goodsObj_del = goodsList_del.getJSONObject(i);
				for (int j = 0; j < goodsList_all.size(); j++) {
					JSONObject goodsObj = JSONObject.fromObject(goodsList_all.get(j));
					System.out.println("goodsObj_del.getString('tags')=="+goodsObj_del.getString("tags"));
					if (goodsObj.get("goodsId") != null 
							&& goodsObj_del.getString("goodsId").equals(goodsObj.get("goodsId").toString())  
							&& goodsObj_del.getString("tags").equals(goodsObj.get("tags").toString())) {
						continue;
					}
					goodsList_temp.add(goodsObj);
				}
				goodsList_all = goodsList_temp;
			}
			
			cart.setGoodsList(goodsList_temp.toString());
			cart.setUpdateTime(System.currentTimeMillis());
			CartDao.update(cart);
		}
		
		JSONArray arrOut = getCartlist(cart.getGoodsList());
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车列表");
		obj.put("data", arrOut);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 全部删除 */
	@RequestMapping(value ="deleteAll",method=RequestMethod.POST)
	@ResponseBody
	public void deleteAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("------------/cart/deleteAll-------------");
		
		String token = request.getParameter("token");
		
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
		
		CartBean cart = CartDao.loadByUid(user.getUid());
		
		CartDao.deleteByCartId(cart.getCartId());
		
		JSONArray arrOut = getCartlist(cart.getGoodsList());
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车列表");
		obj.put("data", arrOut);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/**
	 * 获得输出客户端的购物车列表
	 * @param goodsList
	 * @return
	 */
	public JSONArray getCartlist(String goodsList) {
		JSONArray goodsList_out = JSONArray.fromObject(goodsList);
		Map<Long, JSONArray> goodsMap = new HashMap<Long, JSONArray>();
		for (int i = 0; i < goodsList_out.size(); i++) {
			JSONArray goodsArr = new JSONArray();
			JSONObject goodsObj = JSONObject.fromObject(goodsList_out.get(i));
			GoodsBean goods = GoodsDao.loadByGoodsId(goodsObj.getLong("goodsId"));
			ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
			if (goodsMap.get(shop.getShopId()) == null) {
				goodsArr.add(goodsObj);
				goodsMap.put(shop.getShopId(), goodsArr);
			} else {
				goodsArr = goodsMap.get(shop.getShopId());
				goodsArr.add(goodsObj);
				goodsMap.put(shop.getShopId(), goodsArr);
			}
		}
		
		JSONArray arr = new JSONArray();
		JSONObject obj2 = new JSONObject();
		for (Map.Entry<Long, JSONArray> map : goodsMap.entrySet()) {
			ShopBean shop = ShopDao.loadByShopId(map.getKey());
			obj2 = new JSONObject();
			obj2.put("shopId", shop.getShopId());
			obj2.put("shopName", shop.getName());
			obj2.put("shopImage", shop.getImage());
			obj2.put("shopThumb", shop.getThumbnail());
			JSONArray arr2 = new JSONArray();
			for (int i = 0; i < map.getValue().size(); i++) {
				JSONObject obj3 = JSONObject.fromObject(map.getValue().get(i));
				JSONObject obj4 = JSONObject.fromObject(map.getValue().get(i));
				GoodsBean goods = GoodsDao.loadByGoodsId(obj3.getLong("goodsId"));
				obj4.put("goodsId", obj3.get("goodsId"));
				obj4.put("goodsName", goods.getName());
				obj4.put("goodsLogo", goods.getLogo());
				obj4.put("goodsLogoThumb", goods.getLogoThumb());
				obj4.put("prePrice", goods.getPrePrice());
				obj4.put("curPrice", goods.getCurPrice());
				obj4.put("amount", obj3.get("amount"));
				obj4.put("tags", obj3.get("tags"));
				arr2.add(obj4);
			}
			obj2.put("goodsList", arr2);
			arr.add(obj2);
		}
		
		return arr;
	}
	
}

