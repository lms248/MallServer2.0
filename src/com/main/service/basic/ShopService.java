package main.service.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.bean.admin.User;
import main.bean.basic.ShopBean;
import main.dao.basic.ShopDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;

/**
 * 店铺
 */
@Controller
@RequestMapping("/shop")
public class ShopService {
	
	@Autowired  
    private ShopDao shopDao;
	
	/** 添加店铺 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("------------/shop/add-------------");
		
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
		
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String contactPhone = request.getParameter("contactPhone");
		String logoAndThumb = request.getParameter("logoAndThumb");
		String imageAndThumb = request.getParameter("imageAndThumb");
		String[] logo = logoAndThumb.split(";");
		String[] image = imageAndThumb.split(";");
		
		if (shopDao.loadByShopname(name) != null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该店铺名已存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String shopId = IdGen.get().nextId()+"";
		
		ShopBean shop = new ShopBean();
		shop.setShopId(shopId);
		shop.setName(name);
		shop.setTitle(title);
		shop.setDetails(details);
		shop.setLogo(logo[0]);
		shop.setLogoThumb(logo[1]);
		shop.setImage(image[0]);
		shop.setThumbnail(image[1]);
		shop.setContactPhone(contactPhone);
		shop.setCreateTime(System.currentTimeMillis());
		
		shopDao.save(shop);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加店铺成功");
		obj.put("data", JsonUtils.jsonFromObject(shopDao.loadByShopId(shopId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 修改店铺 */
	@RequestMapping(value ="update",method=RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("------------/shop/update-------------");
		
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
		
		String shopId = request.getParameter("shopId");
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String contactPhone = request.getParameter("contactPhone");
		String logoAndThumb = request.getParameter("logoAndThumb");
		String imageAndThumb = request.getParameter("imageAndThumb");
		String[] logo = logoAndThumb.split(";");
		String[] image = imageAndThumb.split(";");
		
		ShopBean shop = shopDao.loadByShopId(shopId);
		
		if (shop == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该店铺(shopId:"+shopId+")不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		shop.setName(name);
		shop.setTitle(title);
		shop.setDetails(details);
		shop.setLogo(logo[0]);
		shop.setLogoThumb(logo[1]);
		shop.setImage(image[0]);
		shop.setThumbnail(image[1]);
		shop.setContactPhone(contactPhone);
		
		shopDao.update(shop);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "修改店铺成功");
		obj.put("data", JsonUtils.jsonFromObject(shopDao.loadByShopId(shop.getShopId())));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 店铺信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String shopId = request.getParameter("shopId"); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "店铺信息");
		obj.put("data", JsonUtils.jsonFromObject(shopDao.loadByShopId(shopId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 店铺列表 */
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
		
		//店铺查询属性,用于搜索
		String searchContent = request.getParameter("searchContent");
		if (StringUtils.isBlank(searchContent)) {
			searchContent = "";
		}
		
		List<ShopBean> shopList = shopDao.loadAllShop_search(searchContent, index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < shopList.size(); i++) {
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(shopList.get(i)));
			obj2.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(shopList.get(i).getCreateTime())));
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "店铺列表");
		obj.put("count", shopDao.count());
		obj.put("data", arr);
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
		
		System.out.println("------------/shop/delete-------------");
		
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
		
		String shopId = request.getParameter("shopId");
		
		ShopBean shop = shopDao.loadByShopId(shopId);
		
		int result = shopDao.deleteByShopId(shop.getShopId());
		if (result == -1) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "删除店铺失败");
			obj.put("data", "");
			out.print(obj);
		} else {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除店铺成功");
			out.print(obj);
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
