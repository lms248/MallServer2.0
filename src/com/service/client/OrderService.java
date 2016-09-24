package service.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.OrderBean;
import bean.client.PayBean;
import bean.client.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import dao.client.GoodsDao;
import dao.client.OrderDao;
import dao.client.PayDao;
import dao.client.UserDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单
 */
@Controller
@RequestMapping("/order")
public class OrderService {
	
	/** 创建订单 */
	@RequestMapping(value ="create",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String shopList = request.getParameter("shopList");
		
		JSONObject obj = new JSONObject();
		
		if (token == null || shopList == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不正确");
			out.print(obj);
			return;
		}
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		long payId = IdGen.get().nextId();//支付ID
		long createTime = System.currentTimeMillis();
		
		JSONArray shopArr = new JSONArray();
		JSONArray goodsArr = new JSONArray();
		try {
			shopArr = JSONArray.fromObject(shopList);
		} catch (Exception e) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数解析出错");
			out.print(obj);
			return;
		}
		
		double totalPrice = 0;
		for (int i = 0; i < shopArr.size(); i++) {
			JSONObject shopObj = (JSONObject) shopArr.get(i);
			OrderBean order = new OrderBean();
			long orderId = IdGen.get().nextId();//订单ID
			order.setOrderId(orderId);
			order.setPayId(payId);
			order.setUid(user.getUid());
			order.setShopId(shopObj.getLong("shopId"));
			order.setGoodsList(shopObj.getString("goodsList"));
			order.setAddressId(shopObj.getLong("addressId"));
			order.setStatus(0);
			order.setCreatTime(createTime);
			OrderDao.save(order);
			
			goodsArr = JSONArray.fromObject(shopObj.get(i));
			for (int j = 0; j < goodsArr.size(); j++) {
				JSONObject goodsObj = (JSONObject) goodsArr.get(i);
				totalPrice += GoodsDao.loadByGoodsId(goodsObj.getLong("goodsId")).getCurPrice();
			}
		}
		
		PayBean pay = new PayBean();
		pay.setPayId(payId);
		pay.setStatus(0);
		pay.setCreateTime(createTime);
		PayDao.save(pay);
		
		JSONObject payObj = JSONObject.fromObject(pay);
		payObj.put("totalPrice", totalPrice);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "创建订单成功");
		obj.put("data", payObj);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 删除订单 */
	@RequestMapping(value ="delete",method=RequestMethod.POST)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String orderId = request.getParameter("orderId");
		
		JSONObject obj = new JSONObject();
		
		if (token==null || orderId==null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不正确");
			out.print(obj);
			return;
		}
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		OrderBean order = OrderDao.loadByOrderId(Integer.parseInt(orderId));
		if (order == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该订单不存在");
			out.print(obj);
			return;
		}
		
		int result = OrderDao.deleteByOrderId(Integer.parseInt(orderId));
		
		if (result == -1) {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除订单异常");
			obj.put("data", order);
			out.print(obj);
		} else {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除订单成功");
			obj.put("data", order);
			out.print(obj);
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	
}
