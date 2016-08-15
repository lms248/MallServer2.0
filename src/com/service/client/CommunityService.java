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
import common.config.Config;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;
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
		
		//读取客户端提交的数据
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		System.out.println("--------------------------publish uploadImage--------------------------");
		System.out.println(multiValueMap);
		System.out.println("-----------------------------------------------------------------------");
		List<MultipartFile> fileList = multiValueMap.get("imageList");
		String token = request.getParameter("token");
		String content = request.getParameter("content");
		String savePath_image = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/image");
		String savePath_thumb = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/thumb");
		String imageList =UploadService.uploadImage(
				fileList, savePath_image, savePath_thumb, Def.COMMUNITY_THUMB_WIDTH, Def.COMMUNITY_THUMB_HEIGHT, false);
		
		System.out.println("----------community::::publish----------");
		System.out.println("token===="+token);
		System.out.println("content===="+content);
		System.out.println("imageList===="+imageList);
		System.out.println("----------------------------------------");
		
		long communityId = IdGen.get().nextId();
		UserBean ubean = UserDao.loadByToken(token);
		JSONObject obj = new JSONObject();
		if (ubean == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		JSONArray imageArr = new JSONArray();
		JSONArray thumbArr = new JSONArray();
		JSONObject imageObj = new JSONObject();
		JSONObject thumbObj = new JSONObject();
		
		//StringBuffer imageBuffer = new StringBuffer();
		//StringBuffer thumbBuffer = new StringBuffer();
		for (String image : imageList.split(",")) {
			if (StringUtils.isBlack(image)) {
				return;
			}
			imageObj.put("image", "/upload/image/"+image);
			thumbObj.put("thumb", "/upload/thumb/"+image);
			//imageBuffer.append("/upload/image/").append(image).append(",");
			//thumbBuffer.append("/upload/thumb/").append(image).append(",");
		}
		//删除末尾的逗号
		//imageBuffer.deleteCharAt(imageBuffer.length()-1);
		//thumbBuffer.deleteCharAt(thumbBuffer.length()-1);
		imageArr.add(imageObj);
		thumbArr.add(thumbObj);
		
		CommunityBean cbean = new CommunityBean();
		cbean.setCommunityId(communityId);
		cbean.setUid(ubean.getUid());
		cbean.setContent(content);
		cbean.setImageList(imageArr.toString());
		cbean.setThumbList(thumbArr.toString());
		cbean.setTime(System.currentTimeMillis());
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
		int size = Integer.parseInt(request.getParameter("size"));//索引结束
		
		List<CommunityBean> cb_list = CommunityDao.loadAllCommunity(index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj_c = new JSONObject();
		JSONArray arr_c = new JSONArray();
		for (int i = 0; i < cb_list.size(); i++) {
			UserBean ubean = UserDao.loadByUid(cb_list.get(i).getUid());
			obj_c = JSONObject.fromObject(JsonUtils.jsonFromObject(cb_list.get(i)));
			obj_c.put("nickname", ubean.getNickname());
			obj_c.put("avatar", ubean.getAvatar());
			obj_c.put("thumbAvatar", ubean.getThumbnail());
			arr_c.add(obj_c);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "社区信息");
		obj.put("data", arr_c);
		out.print(obj);
		
		out.flush();
		out.close();
	}
}
