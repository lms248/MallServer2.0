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
		String content = request.getParameter("content");
		String images = request.getParameter("images");
		String[] image = images.split(";");
		
		long shopId = IdGen.get().nextId();
		
		ShopBean shop = new ShopBean();
		shop.setShopId(shopId);
		shop.setName(name);
		shop.setTitle(title);
		shop.setContent(content);
		shop.setImage(image[0]);
		shop.setThumbnail(image[1]);
		
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
}
