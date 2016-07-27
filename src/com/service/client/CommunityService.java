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

import dao.client.CommunityDao;

/**
 * 社区
 */
@Controller
@RequestMapping("/community")
public class CommunityService {
	/** 社区信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int communityId = Integer.parseInt(request.getParameter("communityId")); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "社区信息");
		obj.put("data", JsonUtils.jsonFromObject(CommunityDao.loadByCommunityId(communityId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 发布信息 */
	@RequestMapping(value ="publish",method=RequestMethod.GET)
	@ResponseBody
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//TODO 读取客户端提交的json数据
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "社区信息");
		obj.put("data", "");
		out.print(obj);
		
		out.flush();
		out.close();
	}
}
