package service.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import net.sf.json.JSONObject;

import com.utils.SMSUtil;

import common.utils.Def;
import common.utils.SessionContext;

/**
 * 发生手机验证码
 */
@WebServlet("/common/sendPhoneCode")
public class SendPhoneCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public SendPhoneCodeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("----------sendPhoneCode-----------");
		String phone = request.getParameter("phone");
		System.out.println("phone===="+phone);
		
		String filter  = "^[1]([3]{1}|[4]{1}|[5]{1}|[7]{1}|[8]{1})[0-9]{9}$";
		Pattern p = Pattern.compile(filter);  
		Matcher m = p.matcher(phone);  
		
		JSONObject obj_out = new JSONObject();
    	if(!m.matches()){
    		obj_out.put("code", Def.CODE_FAIL);
    		obj_out.put("msg", "手机号格式不正确");
    		out.println(obj_out);
    		return;
    	}
		
    	Date now = new Date();
		//检测是否在80秒内重复发送
		String session_phone = (String) request.getSession().getAttribute("phone");
		Long session_phoneCode_time = (Long) request.getSession().getAttribute("phoneCode_time");
		if(session_phone!=null && phone.equals(session_phone) && session_phoneCode_time!=null){
			if(now.getTime()-session_phoneCode_time<80000){
				out.print("同一手机号不可以在80秒内重复发送短信验证！");
				return;
			}
		}
		
		int phoneCode = (int) (Math.random()*1000000);
		//将随机数存在session中
		HttpSession session = request.getSession();
		session.setAttribute("phone", phone);
		session.setAttribute("phoneCode", String.valueOf(phoneCode));
		session.setAttribute("createTime", now.getTime());
        //记录session
        SessionContext.addSession(session);
        System.out.println("sessionId===="+session.getId());
        JSONObject obj = new JSONObject();
        obj.put("sessionId", session.getId());
        try {
			int status = SMSUtil.sendSMS_ChinaNet1(phone,  "【义乌商城】您本次的手机验证码是（"+phoneCode+"）。");
			System.out.println("status===="+status);
			if (status==1) {
				System.out.println("手机验证码发生成功");
				obj_out.put("code", Def.CODE_SUCCESS);
				obj_out.put("msg", "手机验证码发生成功");
				obj_out.put("data", obj);
				out.println(obj_out);
				System.out.println(obj_out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        out.flush();
        out.close();
	}
	
}
