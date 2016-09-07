package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import bean.client.UserAddress;
import bean.client.UserBean;

import com.alibaba.fastjson.JSON;

import common.config.Config;
import common.utils.Def;
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
		UserBean user = UserDao.loadByUsername(username);
		JSONObject obj = new JSONObject();
		if(user == null || !password.equals(user.getPassword())){
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "账号或密码错误");
			out.print(obj);
		}
		else {
			String token = UuidUtils.getUuid();
			user.setToken(token);
			UserDao.update(user);
			user.setPassword("****");
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "登录成功");
			obj.put("data", JsonUtils.jsonFromObject(user));
			out.print(obj);
			System.out.println(obj.toString());
			user.setLoginTime(System.currentTimeMillis());
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
		
		String token = request.getParameter("token");
		JSONObject obj = new JSONObject();
		if (token == null) {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "参数不正确");
			out.print(obj);
			return;
		}
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "找不到对应用户");
			out.print(obj);
			return;
		}
		user.setToken("");
		UserDao.update(user);
		
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
		
		/*读取客户端发送的参数*/
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String phoneCode = request.getParameter("phoneCode");
		HttpSession session = request.getSession();
		//从服务器端的session中取出手机号和手机验证码
		String session_phone = null;
		String session_phoneCode = null;
		
		System.out.println("session.getId()===="+session.getId());
		//从服务器端的session中取出手机号和手机验证码
		if (session != null) {
			session_phone = session.getAttribute("phone").toString();
			session_phoneCode = session.getAttribute("phoneCode").toString();
		}
		
		/*读取客户端提交的json数据*/
		/*JSONObject req_obj = HttpUtils.getJson4Stream(request.getInputStream());
		String username = req_obj.getString("username");
		String password = req_obj.getString("password");
		String phoneCode = req_obj.getString("phoneCode");*/
		
		System.out.println("register::::username===="+username);
		System.out.println("register::::password===="+password);
		System.out.println("register::::phoneCode===="+phoneCode);
		System.out.println("register::::session_phone===="+session_phone);
		System.out.println("register::::session_phoneCode===="+session_phoneCode);
		
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
		
		if(session_phone==null || session_phoneCode==null){
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "手机验证码未获取");
			out.print(obj);
			return;
		}
		if (!session_phone.equals(username) || !session_phoneCode.equals(phoneCode)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "手机验证码不正确");
			out.print(obj);
			return;
		}
		
		long uid = IdGen.get().nextId();
		String token = UuidUtils.getUuid();
		
		UserBean user = new UserBean();
		user.setUid(uid);
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(username);
		user.setToken(token);
		user.setNickname(Def.NICKNAME_DEFAULT[(int) (Math.random()*10)]);
		user.setAvatar(Def.AVATAR_DEFAULT);
		user.setThumbnail(Def.AVATAR_DEFAULT);
		user.setRegisterTime(System.currentTimeMillis());
		user.setLoginTime(user.getRegisterTime());
		
		UserDao.save(user);
		
		user.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "注册成功");
		obj.put("data", JsonUtils.jsonFromObject(user));
		out.print(obj);
		System.out.println(obj.toString());
		
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
		UserBean user = UserDao.loadByUsername(username);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		if (password_old == null || !user.getPassword().equals(password_old)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户名或密码不正确");
			out.print(obj);
			return;
		}
		
		user.setPassword(password_new);
		UserDao.update(user);
		String token = UuidUtils.getUuid();
		user.setToken(token);
		user.setPassword(password_new);
		UserDao.update(user);
		
		user.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "修改密码成功");
		obj.put("data", JsonUtils.jsonFromObject(user));
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
		String password = request.getParameter("password");
		String phoneCode = request.getParameter("phoneCode");
		
		HttpSession session = request.getSession();
		//从服务器端的session中取出手机号和手机验证码
		String session_phone = null;
		String session_phoneCode = null;
		
		System.out.println("session.getId()===="+session.getId());
		//从服务器端的session中取出手机号和手机验证码
		if (session != null) {
			session_phone = session.getAttribute("phone").toString();
			session_phoneCode = session.getAttribute("phoneCode").toString();
		}
		
		JSONObject obj = new JSONObject();
		
		if (username == null || password == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户名或密码不正确");
			out.print(obj);
			return;
		}
		
		UserBean user = UserDao.loadByUsername(username);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		user.setPassword(password);
		UserDao.update(user);
		
		if(session_phone==null || session_phoneCode==null){
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "手机验证码未获取");
			out.print(obj);
			return;
		}
		if (!session_phone.equals(username) || !session_phoneCode.equals(phoneCode)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "手机验证码不正确");
			out.print(obj);
			return;
		}
		
		String token = UuidUtils.getUuid();
		user.setToken(token);
		user.setPassword(password);
		UserDao.update(user);
		
		user.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "修改密码成功");
		obj.put("data", JsonUtils.jsonFromObject(user));
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
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
		} else {
			user.setPassword("****");
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "用户信息");
			obj.put("data", JsonUtils.jsonFromObject(user));
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
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			return;
		}
		
		if (nickname != null) {
			user.setNickname(nickname);
		}
		
		MultipartHttpServletRequest multipartRequest = null;
		try {
			multipartRequest = (MultipartHttpServletRequest) request;
		} catch (Exception e) {
			System.out.println("updateInfo::::图片上传格式不对或没上传图片");
		}
		if (multipartRequest != null) {
			MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
			System.out.println("--------------------------publish uploadImage--------------------------");
			System.out.println(multiValueMap);
			System.out.println("-----------------------------------------------------------------------");
			List<MultipartFile> fileList = multiValueMap.get("avatar");
			if (!fileList.isEmpty()) {
				String savePath_image = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/image");
				String savePath_thumb = request.getSession().getServletContext().getRealPath(Config.WEB_BASE+"/upload/thumb");
				JSONObject imageObj =UploadService.uploadImage(
						fileList, savePath_image, savePath_thumb, Def.THUMB_WIDTH_AVATAR, Def.THUMB_HEIGHT_AVATAR, false);
				//头像
				user.setAvatar(JSON.parseArray(imageObj.get("imageList").toString()).get(0).toString());
				//头像缩略图
				user.setThumbnail(JSON.parseArray(imageObj.get("imageList").toString()).get(0).toString());
			} 
		}
		
		//更新数据库
		UserDao.update(user);
		
		user.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "更新个人信息成功");
		obj.put("data", JsonUtils.jsonFromObject(user));
		out.print(obj);
		System.out.println(obj.toString());
		
		out.flush();
		out.close();
	}
	
	/** 添加收货地址 */
	@RequestMapping(value ="address/add",method=RequestMethod.POST)
	@ResponseBody
	public void addAddress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token"); 
		String contact = request.getParameter("contact"); 
		String phone = request.getParameter("phone"); 
		String area = request.getParameter("area"); 
		String address = request.getParameter("address"); 
		String isDefault = request.getParameter("isDefault"); 
		
		JSONObject obj = new JSONObject();
		if (token==null || contact==null || phone==null || area==null || address==null || isDefault==null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不正确");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		JSONArray addressArr = new JSONArray();
		try {
			addressArr = JSONArray.fromObject(user.getAddress());
		} catch (Exception e) {
			addressArr = new JSONArray();
		}
		
		UserAddress userAddress = new UserAddress();
		userAddress.setContact(contact);
		userAddress.setPhone(phone);
		userAddress.setArea(area);
		userAddress.setAddress(address);
		userAddress.setDefault(isDefault.equals("true")?true:false);
		addressArr.add(JSONObject.fromObject(userAddress));
		
		user.setAddress(addressArr.toString());
		UserDao.update(user);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "成功添加收货地址");
		obj.put("data", addressArr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 获取收货地址 */
	@RequestMapping(value ="address/infoList",method=RequestMethod.POST)
	@ResponseBody
	public void getAddressList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		String token = request.getParameter("token"); 
		
		JSONObject obj = new JSONObject();
		if (token==null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不正确");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		JSONArray addressArr = JSONArray.fromObject(user.getAddress());
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "收货地址列表");
		obj.put("data", addressArr);
		out.print(obj);
		
		out.flush();
		out.close();
	}	
}
