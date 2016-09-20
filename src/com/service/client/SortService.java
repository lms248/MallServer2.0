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

import bean.client.SortBean;

import common.utils.Def;
import common.utils.JsonUtils;

import dao.client.SortDao;

/**
 * 商品分类
 */
@Controller
@RequestMapping("/sort")
public class SortService {
	
	/** 分类列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int pid = Integer.parseInt(request.getParameter("pid"));//父类ID
		int type = Integer.parseInt(request.getParameter("type"));//类型
		
		List<SortBean> sortList = SortDao.loadByPidAndType(pid, type);
		
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < sortList.size(); i++) {
			arr.add(JSONObject.fromObject(JsonUtils.jsonFromObject(sortList.get(i))));
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "分类列表");
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
}
