package service.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.UserBean;

import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;

import dao.client.UserDao;

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
		//String username = request.getParameter("username");
		//String password = request.getParameter("password");
		
		/*读取客户端提交的json数据*/
		StringBuffer sb = new StringBuffer("");  
		BufferedReader reader = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf-8"));
		String line;
		while((line = reader.readLine())!=null) {
			System.out.println(line);
			sb.append(line);
		}
		JSONObject req_obj = JSONObject.fromObject(sb.toString());
		String username = req_obj.getString("username");
		String password = req_obj.getString("password");
		
		/*读取数据库数据*/
		UserBean ubean = UserDao.loadByUsername(username);
		JSONObject obj = new JSONObject();
		
		if(ubean!=null && password.equals(ubean.getPassword())){
			request.getSession().setAttribute("username", username.trim());
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "账号或密码错误");
			out.print(obj);
		}
		else {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "登录成功");
			obj.put("data", JsonUtils.jsonFromObject(UserDao.loadByUid(ubean.getUid())));
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
	@RequestMapping(value ="register",method=RequestMethod.GET)
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
		
        StringBuffer sb = new StringBuffer("");  
		BufferedReader reader = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf-8"));
		String line;
		while((line = reader.readLine())!=null) {
			System.out.println(line);
			sb.append(line);
		}
		
		/*读取客户端发送的参数*/
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		/*读取客户端提交的json数据*/
		/*JSONObject req_obj = JSONObject.fromObject(sb.toString());
		String username = req_obj.getString("username");
		String password = req_obj.getString("password");
		String phoneCode = req_obj.getString("phoneCode");*/
		
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
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		
		UserBean ubean = new UserBean();
		ubean.setUid(uid);
		ubean.setUsername(username);
		ubean.setPassword(password);
		ubean.setToken(token);
		ubean.setTime(System.currentTimeMillis());
		
		UserDao.save(ubean);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "注册成功");
		obj.put("data", JsonUtils.jsonFromObject(UserDao.loadByUid(uid)));
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
	
	/** 用户手机找回密码 */
	@RequestMapping(value ="resetPassword",method=RequestMethod.POST)
	@ResponseBody
	public void resetPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//从服务器端的session中取出手机号和手机验证码
		String session_phone = (String) request.getSession().getAttribute("phone");
		String session_phoneCode = (String) request.getSession().getAttribute("phoneCode");
		/*获取需要添加到数据库的数据*/
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String phoneCode = request.getParameter("phoneCode");
		
		if(password==null || password.length()<6){
			out.print("密码长度不能少于6喔！");
			return;
		}
		
		if(session_phone==null || session_phoneCode==null){
			out.print("手机号验证码未获取！");
			return;
		}
		else if(!session_phone.equals(phone) || !session_phoneCode.equals(phoneCode)){
			out.print("手机号和验证码不对应！");
			return;
		}
		
		
		
		request.getSession().setAttribute("username", phone);
		request.getSession().setAttribute("phone", phone);
		
		out.flush();
		out.close();
	}
	
	
}
