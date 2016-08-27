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

import bean.client.GoodsBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import net.sf.json.JSONObject;

/**
 * 商品
 */
@Controller
@RequestMapping("/goods")
public class GoodsService {
	
	/** 添加商店 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String shopId = request.getParameter("shopId");
		String curPrice = request.getParameter("curPrice");
		String prePrice = request.getParameter("prePrice");
		String color = request.getParameter("color");
		String size = request.getParameter("size");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String logos = request.getParameter("images");
		String[] logo = logos.split(";");
		
		if (ShopDao.loadByShopId(Long.parseLong(shopId)) == null) {
			JSONObject obj = new JSONObject();
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商店名不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		long goodsId = IdGen.get().nextId();
		
		GoodsBean goods = new GoodsBean();
		goods.setGoodsId(goodsId);
		goods.setShopId(goodsId);
		goods.setCurPrice(Double.parseDouble(curPrice));
		goods.setPrePrice(Double.parseDouble(prePrice));
		goods.setColor(color);
		goods.setSize(Double.parseDouble(size));
		goods.setName(name);
		goods.setTitle(title);
		goods.setDetails(details);
		goods.setLogo(logo[0]);
		goods.setLogoThumb(logo[1]);
		goods.setCreateTime(System.currentTimeMillis());
		
		GoodsDao.save(goods);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加商品成功");
		obj.put("data", JsonUtils.jsonFromObject(GoodsDao.loadByGoodsId(goodsId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 商品详情 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int goodsId = Integer.parseInt(request.getParameter("goodsId")); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商品详情");
		obj.put("data", JsonUtils.jsonFromObject(GoodsDao.loadByGoodsId(goodsId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
}
