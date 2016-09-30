package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import common.utils.HttpUtils;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;
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
			try {
				session_phone = session.getAttribute("phone").toString();
				session_phoneCode = session.getAttribute("phoneCode").toString();
			} catch (Exception e) {
				session_phone = null;
				session_phoneCode = null;
			}
			
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
		
		String uid = IdGen.get().nextId()+"";
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
		user.setType(Def.USER_TYPE_US);
		user.setRegisterTime(System.currentTimeMillis());
		user.setLoginTime(user.getRegisterTime());
		UserDao.save(user);
		
		user.setPassword("****");
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "注册成功");
		obj.put("data", JsonUtils.jsonFromObject(user));
		out.print(obj);
		
		System.out.println(obj);
		
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("phone", username);
		
		out.flush();
		out.close();
	}
	
	/** 第三方登录 */
	@RequestMapping(value ="thirdLogin",method=RequestMethod.POST)
	@ResponseBody
	public void thirdLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		/*读取客户端发送的参数*/
		String uid = request.getParameter("uid");
		String nickname = request.getParameter("nickname");
		String avatar = request.getParameter("avatar");
		String type = request.getParameter("type");
		
		JSONObject obj = new JSONObject();
		
		if (type.equals(Def.USER_TYPE_US)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户类型不正确");
			out.print(obj);
			return;
		}
		
		String token = UuidUtils.getUuid();
		long nowTime = System.currentTimeMillis();
		
		if (Integer.parseInt(type) == Def.USER_TYPE_WECHAT) { //微信
			String code = request.getParameter("code");
			Map<String,String> params1 = new HashMap<String, String>();
			params1.put("appid", "wxd842e65051017e61");
			params1.put("secret", "873eb6899f5ba9f621fcf51e26e0ccf7");
			params1.put("code", code);
			params1.put("grant_type", "authorization_code");
			
			//通过code获取access_token的接口
			String access_result = HttpUtils.doGet("https://api.weixin.qq.com/sns/oauth2/access_token", params1);
			System.out.println("access_result===="+access_result);
			JSONObject accessObj = JSONObject.fromObject(access_result);
			String wx_openid = accessObj.getString("openid");
			String wx_access_token = accessObj.getString("access_token");
			
			UserBean wx_user = UserDao.loadByUidAndType(wx_openid, Def.USER_TYPE_WECHAT);
			if (wx_user == null) { //注册并登录微信用户
				Map<String,String> params2 = new HashMap<String, String>();
				params1.put("access_token", wx_access_token);
				params1.put("openid", wx_openid);
				//获取用户个人信息
				String userinfo_result = HttpUtils.doGet("https://api.weixin.qq.com/sns/userinfo", params2);
				System.out.println("userinfo_result===="+userinfo_result);
				JSONObject userinfoObj = JSONObject.fromObject(userinfo_result);
				String wx_nickname = userinfoObj.getString("nickname");
				String wx_headimgurl = userinfoObj.getString("headimgurl");
				
				wx_user = new UserBean();
				wx_user.setUid(wx_openid);
				wx_user.setNickname(wx_nickname);
				wx_user.setAvatar(wx_headimgurl);
				wx_user.setThumbnail(wx_headimgurl);
				wx_user.setToken(token);
				wx_user.setType(Integer.parseInt(type));
				wx_user.setLoginTime(nowTime);
				wx_user.setRegisterTime(nowTime);
				UserDao.save(wx_user);
				
				obj.put("code", Def.CODE_SUCCESS);
				obj.put("msg", "微信登录成功");
				obj.put("data", JsonUtils.jsonFromObject(wx_user));
				out.print(obj);
			} else { //微信登录
				wx_user.setToken(token);
				wx_user.setLoginTime(nowTime);
				UserDao.update(wx_user);
				
				obj.put("code", Def.CODE_SUCCESS);
				obj.put("msg", "微信登录成功");
				obj.put("data", JsonUtils.jsonFromObject(wx_user));
				out.print(obj);
			}
			return;
		}
		
		/*读取数据库数据*/
		UserBean user = UserDao.loadByUidAndType(uid, Integer.parseInt(type));
		if(user == null){//注册第三方用户
			user = new UserBean();
			user.setUid(uid);
			user.setNickname(nickname);
			user.setAvatar(avatar);
			user.setThumbnail(avatar);
			user.setToken(token);
			user.setType(Integer.parseInt(type));
			user.setLoginTime(nowTime);
			user.setRegisterTime(nowTime);
			UserDao.save(user);
			
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "登录成功");
			obj.put("data", JsonUtils.jsonFromObject(user));
			out.print(obj);
		} else {
			user.setToken(token);
			user.setLoginTime(nowTime);
			UserDao.update(user);
			
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "登录成功");
			obj.put("data", JsonUtils.jsonFromObject(user));
			out.print(obj);
		}
		
		System.out.println(obj);
		
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
	
	/** 获取用户信息列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.POST)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		
		JSONObject obj = new JSONObject();
		List<UserBean> userList = UserDao.loadAllUser(index, size);
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (UserBean user : userList) {
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(user));
			//转化成字符串类型
			obj2.put("uid", ""+user.getUid());
			obj2.put("loginTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(user.getLoginTime())));
			obj2.put("registerTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(user.getRegisterTime())));
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "用户列表");
		obj.put("count", UserDao.Count());
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
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
		if (token==null || contact==null || phone==null|| area==null || address==null || isDefault==null) {
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
		
		String addressId = IdGen.get().nextId()+"";
		JSONArray addressArr = new JSONArray();
		try {
			if (!StringUtils.isBlank(user.getAddress())) {
				addressArr = JSONArray.fromObject(user.getAddress());
				if (addressArr == null) {
					addressArr = new JSONArray();
					user.setDefaultAddressId(addressId);
				}
			}
		} catch (Exception e) {
			addressArr = new JSONArray();
		}
		
		UserAddress userAddress = new UserAddress();
		userAddress.setAddressId(addressId);
		userAddress.setContact(contact);
		userAddress.setPhone(phone);
		userAddress.setArea(area);
		userAddress.setAddress(address);
		if (isDefault.equals("true")) {
			user.setDefaultAddressId(addressId);
		}
		
		addressArr.add(userAddress);
		user.setAddress(addressArr.toString());
		UserDao.update(user);
		
		JSONObject outObj = new JSONObject();
		if (StringUtils.isBlank(user.getDefaultAddressId())) {
			JSONObject obj2 = JSONObject.fromObject(addressArr.get(0));
			if (obj2 != null && !obj2.isNullObject()) {
				user.setDefaultAddressId(obj2.getString("addressId"));
			}
		}
		outObj.put("defaultAddressId", user.getDefaultAddressId());
		outObj.put("addressList", addressArr);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "成功添加收货地址");
		obj.put("data", outObj);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 修改收货地址 */
	@RequestMapping(value ="address/update",method=RequestMethod.POST)
	@ResponseBody
	public void updateAddress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token"); 
		
		String addressId = request.getParameter("addressId"); 
		String contact = request.getParameter("contact"); 
		String phone = request.getParameter("phone"); 
		String area = request.getParameter("area"); 
		String address = request.getParameter("address"); 
		String isDefault = request.getParameter("isDefault"); 
		
		JSONObject obj = new JSONObject();
		if (token==null || addressId==null || contact==null || phone==null|| area==null || address==null || isDefault==null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不可为空");
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
		if (addressArr == null) {
			return;
		}
		
		JSONArray addressArr_new = new JSONArray();
		for (int i = 0; i < addressArr.size(); i++) {
			JSONObject addressObj = JSONObject.fromObject(addressArr.get(i));
			if (addressId.equals(addressObj.get("addressId")+"")) {
				UserAddress userAddress = new UserAddress();
				userAddress.setAddressId(addressId);
				userAddress.setContact(contact);
				userAddress.setPhone(phone);
				userAddress.setArea(area);
				userAddress.setAddress(address);
				if (isDefault.equals("true")) {
					user.setDefaultAddressId(addressId);
				}
				addressArr_new.add(userAddress);
				continue;
			}
			addressArr_new.add(addressObj);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "成功修改收货地址");
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 获取收货地址 */
	@RequestMapping(value ="address/info",method=RequestMethod.POST)
	@ResponseBody
	public void getAddress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		String token = request.getParameter("token"); 
		String addressId = request.getParameter("addressId"); 
		
		JSONObject obj = new JSONObject();
		if (token==null || addressId==null) {
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
		if (!StringUtils.isBlank(user.getAddress())) {
			addressArr = JSONArray.fromObject(user.getAddress());
		}
		
		JSONObject outObj = new JSONObject();
		for (int i = 0; i < addressArr.size(); i++) {
			outObj = JSONObject.fromObject(addressArr.get(i));
			if (addressId.equals(outObj.getString("addressId"))) {
				break;
			}
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "收货地址信息");
		obj.put("data", outObj);
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 获取收货地址列表 */
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
		
		JSONArray addressArr = new JSONArray();
		if (!StringUtils.isBlank(user.getAddress())) {
			addressArr = JSONArray.fromObject(user.getAddress());
		}
		
		JSONObject outObj = new JSONObject();
		if (!StringUtils.isBlank(user.getAddress())) {
			if (StringUtils.isBlank(user.getDefaultAddressId())) {
				JSONObject obj2 = JSONObject.fromObject(addressArr.get(0));
				if (obj2 != null && !obj2.isNullObject()) {
					user.setDefaultAddressId(obj2.getString("addressId"));
				}
			}
			outObj.put("defaultAddressId", user.getDefaultAddressId());
			outObj.put("addressList", addressArr);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "收货地址列表");
		obj.put("data", outObj);
		out.print(obj);
		
		out.flush();
		out.close();
	}	
	
	/** 删除收货地址 */
	@RequestMapping(value ="address/delete",method=RequestMethod.POST)
	@ResponseBody
	public void deleteAddress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		String token = request.getParameter("token"); 
		String addressId = request.getParameter("addressId"); 
		
		JSONObject obj = new JSONObject();
		if (token==null || addressId==null) {
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
		JSONArray addressArr_new = new JSONArray();
		for (int i = 0; i < addressArr.size(); i++) {
			JSONObject addressObj = addressArr.getJSONObject(i);
			if (addressId.equals(addressObj.get("addressId")+"")) {
				if (addressId.equals(user.getDefaultAddressId())) {
					user.setDefaultAddressId("");
				}
				continue;
			}
			addressArr_new.add(addressObj);
		}
		
		user.setAddress(addressArr_new.toString());
		UserDao.update(user);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "删除地址成功");
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
