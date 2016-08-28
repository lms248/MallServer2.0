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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import service.basic.UploadService;
import bean.client.CommunityBean;
import bean.client.UserBean;

import com.alibaba.fastjson.JSON;
import common.config.Config;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;

import dao.client.CommunityDao;
import dao.client.UserDao;

/**
 * 社区
 */
@Controller
@RequestMapping("/community")
public class CommunityService {
	
	/** 发布信息 */
	@RequestMapping(value ="publish",method=RequestMethod.POST)
	@ResponseBody
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		UserBean ubean = UserDao.loadByToken(token);
		JSONObject obj = new JSONObject();
		if (ubean == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		//读取客户端提交的数据
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		System.out.println("--------------------------publish uploadImage--------------------------");
		System.out.println(multiValueMap);
		System.out.println("-----------------------------------------------------------------------");
		List<MultipartFile> fileList = multiValueMap.get("imageList");
		
		String details = request.getParameter("details");
		String savePath_image = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/image");
		String savePath_thumb = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/thumb");
		JSONObject imageObj = UploadService.uploadImage(
				fileList, savePath_image, savePath_thumb, Def.THUMB_WIDTH_COMMUNITY, Def.THUMB_HEIGHT_COMMUNITY, false);
		
		System.out.println("----------community::::publish----------");
		System.out.println("token===="+token);
		System.out.println("details===="+details);
		System.out.println("imageObj===="+imageObj);
		System.out.println("----------------------------------------");
		
		long communityId = IdGen.get().nextId();
		
		//String[] images = imageList.split(";");
		
		CommunityBean cbean = new CommunityBean();
		cbean.setCommunityId(communityId);
		cbean.setUid(ubean.getUid());
		cbean.setDetails(details);
		cbean.setImageList(JSON.toJSONString(imageObj.get("imageList")));
		cbean.setThumbList(JSON.toJSONString(imageObj.get("thumbList")));
		cbean.setCreateTime(System.currentTimeMillis());
		CommunityDao.save(cbean);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "社区信息");
		obj.put("data", JsonUtils.jsonFromObject(cbean));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 获取社区信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int communityId = Integer.parseInt(request.getParameter("communityId")); 
		
		CommunityBean cbean = CommunityDao.loadByCommunityId(communityId);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "社区信息");
		obj.put("data", JsonUtils.jsonFromObject(cbean));
		out.print(obj);
		System.out.println(obj);
		
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
		
		List<CommunityBean> cb_list = CommunityDao.loadAllCommunity(index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj_c = new JSONObject();
		JSONArray arr_c = new JSONArray();
		for (int i = 0; i < cb_list.size(); i++) {
			UserBean ubean = UserDao.loadByUid(cb_list.get(i).getUid());
			if (ubean == null) {
				try {
					CommunityDao.delete(cb_list.get(i).getId());
				} finally {
					cb_list = CommunityDao.loadAllCommunity(index, size);
					arr_c = new JSONArray();
					i = 0;
				}
				continue;
			}
			obj_c = JSONObject.fromObject(JsonUtils.jsonFromObject(cb_list.get(i)));
			obj_c.put("nickname", ubean.getNickname()+"");
			obj_c.put("avatar", ubean.getAvatar()+"");
			obj_c.put("thumbAvatar", ubean.getThumbnail()+"");
			arr_c.add(obj_c);
		}
		
		if (arr_c.size() > 0) {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "社区列表");
			obj.put("data", arr_c);
			out.print(obj);
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
