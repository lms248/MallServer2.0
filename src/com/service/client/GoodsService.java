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

import common.utils.Def;
import common.utils.JsonUtils;

import dao.client.GoodsDao;

/**
 * 商品
 */
@Controller
@RequestMapping("/goods")
public class GoodsService {
	/** 商品详情 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int goodsid = Integer.parseInt(request.getParameter("goodsid")); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商品详情");
		obj.put("data", JsonUtils.jsonFromObject(GoodsDao.loadByGoodsId(goodsid)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
}