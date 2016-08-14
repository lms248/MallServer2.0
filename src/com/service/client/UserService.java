package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import service.basic.UploadService;
import bean.client.UserBean;
import common.config.Config;
import common.utils.Def;
import common.utils.HttpUtils;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.UuidUtils;
import dao.client.UserDao;

/**
 * 用户
 */
@Controller
@RequestMapping("/user")
public class UserService {
	
	/** 用户登录 */
	@RequestMapping(value ="login",method=RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		/*读取客户端发送的参数*/
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		/*读取客户端提交的json数据*/
		/*JSONObject req_obj = HttpUtils.getJson4Stream(request.getInputStream());
		String username = req_obj.getString("username");
		String password = req_obj.getString("password");*/
		
		/*读取数据库数据*/
		UserBean ubean = UserDao.loadByUsername(username);
		JSONObject obj = new JSONObject();
		if(ubean == null || !password.equals(ubean.getPassword())){
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "账号或密码错误");
			out.print(obj);
		}
		else {
			ubean.setPassword("****");
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "登录成功");
			obj.put("data", JsonUtils.jsonFromObject(ubean));
			out.print(obj);
			System.out.println(obj.toString());
		}
		
		out.flush();
		out.close();
	}
	
	
	/** 用户注销 */
	@RequestMapping(value ="logout",method=RequestMethod.POST)
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		request.getSession().setAttribute("username", "");
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "成功注销登录");
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 用户手机注册 */
	@RequestMapping(value ="register",method=RequestMethod.POST)
	@ResponseBody
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//从服务器端的session中取出手机号和手机验证码
		String session_phone = (String) request.getSession().getAttribute("phone");
		String session_phoneCode = (String) request.getSession().getAttribute("phoneCode");
		
		/*读取客户端发送的参数*/
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		/*读取客户端提交的json数据*/
		/*JSONObject req_obj = HttpUtils.getJson4Stream(request.getInputStream());
		String username = req_obj.getString("username");
		String password = req_obj.getString("password");
		String phoneCode = req_obj.getString("phoneCode");*/
		
		System.out.println("register::::usernam===="+username);
		System.out.println("register::::password===="+password);
		
		JSONObject obj = new JSONObject();
		
		if (username==null || password==null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "手机号或密码错误");
			out.print(obj);
			return;
		}
		
		if (UserDao.loadByUsername(username) != null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该用户已存在");
			out.print(obj);
			return;
		}
		
		long uid = IdGen.get().nextId();
		String token = UuidUtils.getUuid();
		
		UserBean ubean = new UserBean();
		ubean.setUid(uid);
		ubean.setUsername(username);
		ubean.setPassword(password);
		ubean.setPhone(username);
		ubean.setToken(token);
		ubean.setTime(System.currentTimeMillis());
		
		UserDao.save(ubean);
		
		ubean.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "注册成功");
		obj.put("data", JsonUtils.jsonFromObject(ubean));
		out.print(obj);
		System.out.println(obj.toString());
		
		/*if(session_phone==null || session_phoneCode==null){
			out.print("手机号验证码未获取！");
			return;
		}
		else if(!session_phone.equals(phone) || !session_phoneCode.equals(phoneCode)){
			out.print("手机号和验证码不对应！");
			return;
		}*/
		
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("phone", username);
		
		out.flush();
		out.close();
	}
	
	/** 修改密码 */
	@RequestMapping(value ="updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public void updatePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		/*读取客户端提交的json数据*/
		/*JSONObject req_obj = HttpUtils.getJson4Stream(request.getInputStream());
		String username = req_obj.getString("username");
		String password_old = req_obj.getString("password_old");
		String password_new = req_obj.getString("password_new");*/
		String username = request.getParameter("username");
		String password_old = request.getParameter("password_old");
		String password_new = request.getParameter("password_new");
		
		JSONObject obj = new JSONObject();
		UserBean ubean = UserDao.loadByUsername(username);
		if (ubean == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		if (password_old == null || !ubean.getPassword().equals(password_old)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户名或密码不正确");
			out.print(obj);
			return;
		}
		
		ubean.setPassword(password_new);
		UserDao.update(ubean);
		
		ubean.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "修改密码成功");
		obj.put("data", JsonUtils.jsonFromObject(ubean));
		out.print(obj);
		System.out.println(obj.toString());
		
		
		out.flush();
		out.close();
	}
	
	/** 用户手机找回密码 */
	@RequestMapping(value ="findPassword",method=RequestMethod.POST)
	@ResponseBody
	public void findPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		/*读取客户端提交的json数据*/
		/*JSONObject req_obj = HttpUtils.getJson4Stream(request.getInputStream());
		String username = req_obj.getString("username");
		String password_old = req_obj.getString("password_old");
		String password_new = req_obj.getString("password_new");*/
		//String phoneCode = req_obj.getString("phoneCode");
		String username = request.getParameter("username");
		String password_new = request.getParameter("password_new");
		String phoneCode = request.getParameter("phoneCode");
		
		
		JSONObject obj = new JSONObject();
		
		if (username == null || password_new == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户名或密码不正确");
			out.print(obj);
			return;
		}
		
		UserBean ubean = UserDao.loadByUsername(username);
		if (ubean == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		ubean.setPassword(password_new);
		UserDao.update(ubean);
		
		ubean.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "修改密码成功");
		obj.put("data", JsonUtils.jsonFromObject(ubean));
		out.print(obj);
		System.out.println(obj.toString());
		
		
		out.flush();
		out.close();
	}
	
	/** 获取用户信息 */
	@RequestMapping(value ="info",method=RequestMethod.POST)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token"); 
		
		JSONObject obj = new JSONObject();
		UserBean ubean = UserDao.loadByToken(token);
		if (ubean == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
		} else {
			ubean.setPassword("****");
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "用户信息");
			obj.put("data", JsonUtils.jsonFromObject(ubean));
			out.print(obj);
		}
		
		out.flush();
		out.close();
	}
	
	/**
	 * 更新个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value ="updateInfo",method=RequestMethod.POST)
	@ResponseBody
	public void updateInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//读取客户端提交的数据
		String token = request.getParameter("token");
		String nickname = request.getParameter("nickname");
		
		JSONObject obj = new JSONObject();
		UserBean ubean = UserDao.loadByToken(token);
		if (ubean == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		ubean.setNickname(nickname);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		System.out.println("--------------------------publish uploadImage--------------------------");
		System.out.println(multiValueMap);
		System.out.println("-----------------------------------------------------------------------");
		List<MultipartFile> fileList = multiValueMap.get("avatar");
		if (!fileList.isEmpty()) {
			String savePath_image = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/image");
			String savePath_thumb = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/thumb");
			String image =UploadService.uploadImage(
					fileList, savePath_image, savePath_thumb, Def.COMMUNITY_THUMB_WIDTH, Def.COMMUNITY_THUMB_HEIGHT, false);
			//头像
			ubean.setAvatar(savePath_image+"/"+image);
			//头像缩略图
			ubean.setThumbnail(savePath_thumb+"/"+image);
		} 
		
		//更新数据库
		UserDao.update(ubean);
		
		ubean.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "更新个人信息成功");
		obj.put("data", JsonUtils.jsonFromObject(ubean));
		out.print(obj);
		System.out.println(obj.toString());
		
		out.flush();
		out.close();
	}
}
