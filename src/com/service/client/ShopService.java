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

import bean.client.ShopBean;

import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;

import dao.client.ShopDao;

/**
 * 商店
 */
@Controller
@RequestMapping("/shop")
public class ShopService {
	
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
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String contactPhone = request.getParameter("contactPhone");
		String images = request.getParameter("images");
		String[] image = images.split(";");
		
		if (ShopDao.loadByShopname(name) != null) {
			JSONObject obj = new JSONObject();
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商店名已存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		long shopId = IdGen.get().nextId();
		
		ShopBean shop = new ShopBean();
		shop.setShopId(shopId);
		shop.setName(name);
		shop.setTitle(title);
		shop.setDetails(details);
		shop.setImage(image[0]);
		shop.setThumbnail(image[1]);
		shop.setContactPhone(contactPhone);
		shop.setCreateTime(System.currentTimeMillis());
		
		ShopDao.save(shop);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加商店成功");
		obj.put("data", JsonUtils.jsonFromObject(ShopDao.loadByShopId(shopId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 商店信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int shopId = Integer.parseInt(request.getParameter("shopId")); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商店信息");
		obj.put("data", JsonUtils.jsonFromObject(ShopDao.loadByShopId(shopId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 社区信息列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		
		List<ShopBean> shopList = ShopDao.loadAllShop(index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < shopList.size(); i++) {
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(shopList.get(i)));
			//转化成字符串类型
			obj2.put("shopId", ""+shopList.get(i).getShopId());
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商店列表");
		obj.put("count", ShopDao.Count());
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
