package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import bean.admin.User;
import bean.client.CommentBean;
import bean.client.GoodsBean;
import bean.client.OrdersBean;
import bean.client.ShopBean;
import bean.client.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;
import dao.client.CommentDao;
import dao.client.GoodsDao;
import dao.client.OrdersDao;
import dao.client.ShopDao;
import dao.client.UserDao;

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
		String isFromCart = request.getParameter("isFromCart");
		
		System.out.println("shopList===="+shopList);
		System.out.println("isFromCart===="+isFromCart);
		
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
		
		//String payId = IdGen.get().nextId()+"";//支付ID
		long createTime = System.currentTimeMillis();
		
		JSONArray shopArr = new JSONArray();
		JSONArray goodsArr = new JSONArray();
		com.alibaba.fastjson.JSONArray orderIds = new com.alibaba.fastjson.JSONArray();
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
			JSONObject shopObj = JSONObject.fromObject(shopArr.get(i));
			
			goodsArr = JSONArray.fromObject(shopObj.getString("goodsList"));
			for (int j = 0; j < goodsArr.size(); j++) {
				JSONObject goodsObj = JSONObject.fromObject(goodsArr.get(j));
				totalPrice += GoodsDao.loadByGoodsId(goodsObj.getString("goodsId")).getCurPrice();
				if (isFromCart != null && isFromCart.equals("true")) {
					System.out.println("#goodsId===="+goodsObj.getString("goodsId"));
					System.out.println("#tags===="+goodsObj.getString("tags"));
					int deleteRs = CartService.deleteCart(token, goodsObj.getString("goodsId"), goodsObj.getString("tags"));
					System.out.println("#deleteRs===="+deleteRs);
				}
			}
			
			OrdersBean order = new OrdersBean();
			String orderId = IdGen.get().nextId()+"";//订单ID
			order.setOrderId(orderId);
			//order.setPayId(payId);
			order.setUid(user.getUid());
			order.setShopId(shopObj.getString("shopId"));
			order.setGoodsList(shopObj.getString("goodsList"));
			order.setAddressId(shopObj.getLong("addressId"));
			order.setStatus(Def.ORDER_STATUS_NOPAY);
			order.setCreateTime(createTime);
			
			OrdersDao.save(order);
			orderIds.add(orderId);
		}
		
		/*PayBean pay = new PayBean();
		pay.setPayId(payId);
		pay.setStatus(Def.ORDER_STATUS_NOPAY);
		pay.setCreateTime(createTime);
		PayDao.save(pay);
		
		JSONObject payObj = JSONObject.fromObject(pay);*/
		
		JSONObject orderObject = new JSONObject();
		orderObject.put("totalPrice", totalPrice);
		orderObject.put("orderIds", orderIds);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "创建订单成功");
		obj.put("data", orderObject);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 订单详情 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String orderId = request.getParameter("orderId");
		
		JSONObject obj = new JSONObject();
		JSONObject orderObj = new JSONObject();
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		OrdersBean order = OrdersDao.loadByOrderId(orderId);
		orderObj = JSONObject.fromObject(JsonUtils.jsonFromObject(order));
		ShopBean shop = ShopDao.loadByShopId(order.getShopId());
		if (shop == null) {
			return;
		}
		JSONArray goodsList = JSONArray.fromObject(order.getGoodsList());
		JSONArray goodsArr = new JSONArray();
		JSONObject goodsObj = new JSONObject();
		JSONArray commentArr = new JSONArray();
		double totalPrice = 0;
		for (int i = 0; i < goodsList.size(); i++) {
			goodsObj = JSONObject.fromObject(goodsList.get(i));
			GoodsBean goods = GoodsDao.loadByGoodsId(goodsObj.getString("goodsId"));
			if (goods == null) {
				continue;
			}
			goodsObj.put("goodsName", goods.getName());
			goodsObj.put("goodsLogo", goods.getLogo());
			goodsObj.put("goodsLogoThumb", goods.getLogoThumb());
			goodsObj.put("prePrice", goods.getPrePrice());
			goodsObj.put("curPrice", goods.getCurPrice());
			goodsArr.add(goodsObj);
			totalPrice += goods.getCurPrice() * goodsObj.getInt("amount");
			
			List<CommentBean> commentList = CommentDao.loadByUidAndGoodsId(user.getUid(), goods.getGoodsId());
			for (CommentBean comment : commentList) {
				commentArr.add(JSONObject.fromObject(comment));
			}
		}
		orderObj.put("shopName", shop.getName());
		orderObj.put("shopLogo", shop.getLogo());
		orderObj.put("shopLogoThumb", shop.getLogoThumb());
		orderObj.put("contactPhone", shop.getContactPhone());
		orderObj.put("goodsList", goodsArr);
		orderObj.put("totalPrice", totalPrice);
		
		JSONArray addressArr = new JSONArray();
		if (!StringUtils.isBlank(user.getAddress())) {
			addressArr = JSONArray.fromObject(user.getAddress());
		}
		JSONObject addressObj = new JSONObject();
		for (int i = 0; i < addressArr.size(); i++) {
			addressObj = JSONObject.fromObject(addressArr.get(i));
			if (order.getAddressId() == addressObj.getLong("addressId")) {
				break;
			}
		}
		orderObj.put("address", addressObj);
		orderObj.put("comment", commentArr);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "订单详情");
		obj.put("data", orderObj);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 订单列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String status = request.getParameter("status");
		
		JSONObject obj = new JSONObject();
		
		if (token==null || status == null) {
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
		
		List<OrdersBean> orderList = OrdersDao.loadByUidAndStatus(user.getUid(), Integer.parseInt(status));
		JSONObject orderObj = new JSONObject();
		JSONArray orderArr = new JSONArray();
		for (OrdersBean order : orderList) {
			orderObj = JSONObject.fromObject(JsonUtils.jsonFromObject(order));
			ShopBean shop = ShopDao.loadByShopId(order.getShopId());
			if (shop == null) {
				continue;
			}
			JSONArray goodsList = JSONArray.fromObject(order.getGoodsList());
			JSONArray goodsArr = new JSONArray();
			JSONObject goodsObj = new JSONObject();
			JSONArray commentArr = new JSONArray();
			for (int i = 0; i < goodsList.size(); i++) {
				goodsObj = JSONObject.fromObject(goodsList.get(i));
				GoodsBean goods = GoodsDao.loadByGoodsId(goodsObj.getString("goodsId"));
				if (goods == null) {
					continue;
				}
				goodsObj.put("goodsName", goods.getName());
				goodsObj.put("goodsLogo", goods.getLogo());
				goodsObj.put("goodsLogoThumb", goods.getLogoThumb());
				goodsObj.put("prePrice", goods.getPrePrice());
				goodsObj.put("curPrice", goods.getCurPrice());
				goodsArr.add(goodsObj);
				
				List<CommentBean> commentList = CommentDao.loadByUidAndGoodsId(user.getUid(), goods.getGoodsId());
				for (CommentBean comment : commentList) {
					commentArr.add(JSONObject.fromObject(comment));
				}
			}
			orderObj.put("shopName", shop.getName());
			orderObj.put("shopLogo", shop.getLogo());
			orderObj.put("shopLogoThumb", shop.getLogoThumb());
			orderObj.put("contactPhone", shop.getContactPhone());
			orderObj.put("goodsList", goodsArr);
			JSONArray addressArr = new JSONArray();
			if (!StringUtils.isBlank(user.getAddress())) {
				addressArr = JSONArray.fromObject(user.getAddress());
			}
			
			JSONObject addressObj = new JSONObject();
			for (int i = 0; i < addressArr.size(); i++) {
				addressObj = JSONObject.fromObject(addressArr.get(i));
				if (order.getAddressId() == addressObj.getLong("addressId")) {
					break;
				}
			}
			orderObj.put("address", addressObj);
			orderObj.put("comment", commentArr);
			orderArr.add(orderObj);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "订单列表");
		obj.put("data", orderArr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 订单列表后台管理 */
	@RequestMapping(value ="infoList2",method=RequestMethod.GET)
	@ResponseBody
	public void infoList2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		User admin_user = (User) request.getSession().getAttribute("admin_user");
		if (admin_user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "未登录或登录已过期，请重新登录");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		String status = request.getParameter("status");//订单状态
		
		List<OrdersBean> orderList = new ArrayList<OrdersBean>();
		int orderCount = 0;
		
		if (StringUtils.isBlank(status) || Integer.parseInt(status) == -1) {
			orderList = OrdersDao.loadAllOrder(index, size);
			orderCount = OrdersDao.Count();
		} else {
			orderList = OrdersDao.loadOrderByStatus(index, size, Integer.parseInt(status));
			orderCount = OrdersDao.Count(Integer.parseInt(status));
		}
		
		JSONObject orderObj = new JSONObject();
		JSONArray orderArr = new JSONArray();
		for (OrdersBean order : orderList) {
			orderObj = JSONObject.fromObject(JsonUtils.jsonFromObject(order));
			ShopBean shop = ShopDao.loadByShopId(order.getShopId());
			if (shop == null) {
				continue;
			}
			orderObj.put("shopName", shop.getName());
			orderObj.put("shopLogo", shop.getLogo());
			orderObj.put("shopLogoThumb", shop.getLogoThumb());
			orderObj.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getCreateTime())));
			orderArr.add(orderObj);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "订单列表");
		obj.put("count", orderCount);
		obj.put("data", orderArr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 确认收货 */
	@RequestMapping(value ="receive",method=RequestMethod.POST)
	@ResponseBody
	public void receive(HttpServletRequest request, HttpServletResponse response)
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
		
		OrdersBean order = OrdersDao.loadByOrderId(orderId);
		if (order == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该订单不存在");
			out.print(obj);
			return;
		}
		
		order.setStatus(Def.ORDER_STATUS_RECEIVE);
		order.setReceiveTime(System.currentTimeMillis());
		OrdersDao.update(order);
		MessageService.addMessage(order.getUid(), "【订单状态】", MessageService.getOrderMessage(orderId, order.getStatus()));
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "确认收货成功");
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 取消订单 */
	@RequestMapping(value ="cancel",method=RequestMethod.POST)
	@ResponseBody
	public void cancel(HttpServletRequest request, HttpServletResponse response)
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
		
		OrdersBean order = OrdersDao.loadByOrderId(orderId);
		if (order == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该订单不存在");
			out.print(obj);
			return;
		}
		
		order.setStatus(Def.ORDER_STATUS_CANCEL);
		OrdersDao.update(order);
		MessageService.addMessage(order.getUid(), "【订单状态】", MessageService.getOrderMessage(orderId, order.getStatus()));
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "取消订单成功");
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 申请售后服务 */
	@RequestMapping(value ="applyAfterSaleService",method=RequestMethod.POST)
	@ResponseBody
	public void applyAfterSaleService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String orderId = request.getParameter("orderId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		JSONObject obj = new JSONObject();
		
		if (token==null || orderId==null || title==null || content==null) {
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
		
		OrdersBean order = OrdersDao.loadByOrderId(orderId);
		if (order == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该订单不存在");
			out.print(obj);
			return;
		}
		
		JSONObject afterSaleObj = new JSONObject();
		afterSaleObj.put("title", title);
		afterSaleObj.put("content", content);
		afterSaleObj.put("status", 0);
		
		order.setStatus(Def.ORDER_STATUS_AFTERSALES);
		order.setAfterSaleService(afterSaleObj.toString());
		OrdersDao.update(order);
		MessageService.addMessage(order.getUid(), "【订单状态】", MessageService.getOrderMessage(orderId, order.getStatus()));
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "申请售后服务成功");
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 更新订单状态 */
	@RequestMapping(value ="updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public void updateStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject obj = new JSONObject();
		User admin_user = (User) request.getSession().getAttribute("admin_user");
		if (admin_user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "未登录或登录已过期，请重新登录");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String orderId = request.getParameter("orderId");
		String newStatus = request.getParameter("newStatus");
		
		OrdersBean order = OrdersDao.loadByOrderId(orderId);
		if (order == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该订单不存在");
			out.print(obj);
			return;
		}
		
		if (Integer.parseInt(newStatus) >= 0 && Integer.parseInt(newStatus) <= 5) {
			order.setStatus(Integer.parseInt(newStatus));
			OrdersDao.update(order);
			
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "更新订单状态成功");
			out.print(obj);
		}
		
		switch (Integer.parseInt(newStatus)) {
		case Def.ORDER_STATUS_NOPAY:
		case Def.ORDER_STATUS_NOTDELIVER:
		case Def.ORDER_STATUS_CANCEL:
		case Def.ORDER_STATUS_AFTERSALES:
		case Def.ORDER_STATUS_NOTRECEIVE://发货
			order.setStatus(Integer.parseInt(newStatus));
			order.setDeliverTime(System.currentTimeMillis());
			OrdersDao.update(order);
			MessageService.addMessage(order.getUid(), "【订单状态】", MessageService.getOrderMessage(orderId, order.getStatus()));
		default:
			break;
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 微信商户平台支付结果通知 */
	@RequestMapping(value ="wxPayResult",method=RequestMethod.POST)
	@ResponseBody
	public void wxPayResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("---------wxPayResult-------------");
		
		out.flush();
		out.close();
	}
	
	/** 支付宝商户平台支付结果通知 */
	@RequestMapping(value ="alipayResult",method=RequestMethod.POST)
	@ResponseBody
	public void alipayResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("---------alipayResult-------------");
		
		out.flush();
		out.close();
	}
	
}
