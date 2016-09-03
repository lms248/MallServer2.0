package service.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import bean.client.CartBean;
import bean.client.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import dao.client.CartDao;
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
			cartObj.put("tags", JSON.parseArray(tags));
			JSONArray goodsList = new JSONArray();
			goodsList.add(cartObj);
			
			cart.setGoodsList(goodsList.toString());
			cart.setUpdateTime(System.currentTimeMillis());
			CartDao.save(cart);
		} else {
			cartObj.put("goodsId", Long.parseLong(goodsId));
			cartObj.put("amount", Integer.parseInt(amount));
			cartObj.put("tags", JSON.parseArray(tags));
			JSONArray goodsList = JSONArray.fromObject(cart.getGoodsList());
			goodsList.add(cartObj);
			
			cart.setGoodsList(goodsList.toString());
			cart.setUpdateTime(System.currentTimeMillis());
			CartDao.update(cart);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加购物车成功");
		obj.put("data", JsonUtils.jsonFromObject(CartDao.loadByCartId(cartId)));
		out.print(obj);
		
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
		
		CartBean cart = CartDao.loadByUid(user.getUid());
		
		com.alibaba.fastjson.JSONArray goodsList = JSON.parseArray(cart.getGoodsList());
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = index; i < goodsList.size() && i < size; i++) {
			JSONObject obj3 = JSONObject.fromObject(goodsList.get(i));
			obj2.put("goodsId", obj3.get("goodsId"));
			obj2.put("amount", obj3.get("amount"));
			obj2.put("tags", obj3.get("tags"));
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车列表");
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
