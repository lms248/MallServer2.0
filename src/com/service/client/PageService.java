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
import common.utils.Def;
import dao.client.GoodsDao;

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
		List<GoodsBean> bannerList = GoodsDao.loadByType(Def.GOODS_TYPE_BANNER);
		for (int i = 0; i < bannerList.size(); i++) {
			obj_banner.put("goodid", bannerList.get(i).getGoodsid());
			obj_banner.put("image", bannerList.get(i).getImagelist().split(",")[0]);
			arr_banner.add(obj_banner);
		}
		
		//首页精选促销
		JSONObject obj_promotion = new JSONObject();
		JSONArray arr_promotion = new JSONArray();
		List<GoodsBean> promotionList = GoodsDao.loadByType(Def.GOODS_TYPE_BANNER);
		for (int i = 0; i < promotionList.size(); i++) {
			obj_promotion.put("goodid", promotionList.get(i).getGoodsid());
			obj_promotion.put("image", promotionList.get(i).getImagelist().split(",")[0]);
			arr_promotion.add(obj_promotion);
		}
		
		//TODO 首页商城商户
		
		JSONObject obj_data = new JSONObject();
		obj_data.put("banner", arr_banner);
		obj_data.put("promotion", arr_promotion);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "首页数据");
		obj.put("data", obj_data);
		out.print(obj);
		
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
		//JSONObject obj_banner = new JSONObject();
		//obj_banner.put("image", "");
		//obj_banner.put("url", "");
		obj.put("data", "");
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
