package service.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.GoodsBean;
import bean.client.OrderBean;
import bean.client.UserBean;

import common.utils.Def;
import common.utils.IdGen;

import dao.client.GoodsDao;
import dao.client.OrderDao;
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
		String goodsId = request.getParameter("goodsId");
		String amount = request.getParameter("amount");
		String tags = request.getParameter("tags");
		
		JSONObject obj = new JSONObject();
		
		if (token==null || goodsId==null || amount==null || tags==null) {
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
		
		GoodsBean goods = GoodsDao.loadByGoodsId(Long.parseLong(goodsId));
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "商品不存在");
			out.print(obj);
			return;
		}
		double price = goods.getCurPrice()*Integer.parseInt(amount);
		long orderId = IdGen.get().nextId();
		
		OrderBean order = new OrderBean();
		order.setOrderId(orderId);
		order.setUid(user.getUid());
		order.setGoodsId(goodsId);
		order.setAmount(Integer.parseInt(amount));
		order.setTags(tags);
		order.setPrice(price);
		order.setCreatTime(System.currentTimeMillis());
		
		OrderDao.save(order);
		
		obj.put("code", Def.CODE_FAIL);
		obj.put("msg", "创建订单成功");
		obj.put("data", order);
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
