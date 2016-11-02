package service.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.basic.FeedbackBean;
import bean.basic.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import dao.basic.FeedbackDao;
import dao.basic.UserDao;

/**
 * 反馈
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackService {
	
	@Autowired  
    private UserDao userDao;
	@Autowired  
	private FeedbackDao feedbackDao;
	
	/** 添加反馈 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String info = request.getParameter("info");
		
		JSONObject obj = new JSONObject();
		
		UserBean user = userDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String feedbackId = IdGen.get().nextId()+"";
		
		FeedbackBean feedback = new FeedbackBean();
		feedback.setFeedbackId(feedbackId);
		feedback.setUid(user.getUid());
		feedback.setInfo(info);
		feedback.setCreateTime(System.currentTimeMillis());
		
		feedbackDao.save(feedback);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加反馈成功");
		obj.put("data", JsonUtils.jsonFromObject(feedbackDao.loadByFeedbackId(feedbackId)));
		out.print(obj);
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 反馈信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String feedbackId = request.getParameter("feedbackId"); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "反馈信息");
		obj.put("data", JsonUtils.jsonFromObject(feedbackDao.loadByFeedbackId(feedbackId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 反馈列表 */
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
		
		List<FeedbackBean> feedbackList = feedbackDao.loadAllFeedback(index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (FeedbackBean feedback : feedbackList) {
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(feedback));
			UserBean user = userDao.loadByUid(feedback.getUid());
			obj2.put("username", ""+user==null?"":user.getUsername());
			obj2.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(feedback.getCreateTime())));
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "反馈列表");
		obj.put("count", feedbackDao.count());
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
