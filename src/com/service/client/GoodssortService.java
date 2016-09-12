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

import bean.client.GoodssortBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import dao.client.GoodssortDao;
import dao.client.ShopDao;

/**
 * 商品分类
 */
@Controller
@RequestMapping("/goodsSort")
public class GoodssortService {
	
	/** 添加商品分类 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String level_1 = request.getParameter("level_1");
		String level_2 = request.getParameter("level_2");
		String level_3 = request.getParameter("level_3");
		String goodsId = request.getParameter("goodsId");
		
		long goodssortId = IdGen.get().nextId();
		
		GoodssortBean goodssort = new GoodssortBean();
		goodssort.setGoodssortId(goodssortId);
		goodssort.setLevel_1(Integer.parseInt(level_1));
		goodssort.setLevel_2(Integer.parseInt(level_2));
		goodssort.setLevel_3(Integer.parseInt(level_3));
		goodssort.setGoodsId(Long.parseLong(goodsId));
		
		int result = GoodssortDao.save(goodssort);
		
		if (result != -1) {
			JSONObject obj = new JSONObject();
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "添加店铺成功");
			obj.put("data", JsonUtils.jsonFromObject(ShopDao.loadByShopId(goodssortId)));
			out.print(obj);
		}
		
		out.flush();
		out.close();
	}
	
}
