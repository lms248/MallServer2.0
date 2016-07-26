package service.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bean.admin.User;

import common.logger.Logger;
import common.logger.LoggerManager;

@Controller
@RequestMapping("/admin")
public class AdminService {
	private static Logger log = LoggerManager.getLogger();
	
	/** 登录界面 **/
	@RequestMapping(value="login", method=RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView view = new ModelAndView("admin/login");
		view.addObject("username", "mose");
		view.addObject("password", "123456");
		
		//log.info("Mose open the (/admin/login) page.");
        return view;
	}
	
	/** 退出登录 **/
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public ModelAndView logout(){
		ModelAndView view = new ModelAndView("admin/login");
		view.addObject("username", "mose");
		view.addObject("password", "123456");
		
		log.info("Mose open the (/admin/login) page.");
		return view;
	}
	
	/** 管理主界面 **/
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		if (session.getAttribute("admin_user")==null) {
			try {
				response.sendRedirect("/admin/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ModelAndView view = new ModelAndView("admin/index");
		view.addObject("username", "mose");
		return view;
	}
	
	/** 测试 **/
	@RequestMapping(value="test", method=RequestMethod.GET)
	@ResponseBody
	public User test(){
		User user = new User();
		user.setName("1111");
		user.setId(1);
		return user;
	}
	
	/** 权限管理 **/
	@RequestMapping(value="authority", method=RequestMethod.POST)
	public ModelAndView authority(){
		ModelAndView view = new ModelAndView("admin/authority");
		return view;
	}
	
	/** 用户管理 **/
	@RequestMapping(value="user", method=RequestMethod.POST)
	public ModelAndView user(){
		ModelAndView view = new ModelAndView("admin/user");
		return view;
	}
	
	/** 公会管理 **/
	@RequestMapping(value="guild", method=RequestMethod.POST)
	public ModelAndView guild(){
		ModelAndView view = new ModelAndView("admin/guild");
		return view;
	}
	
	/** 游戏中心 **/
	@RequestMapping(value="game", method=RequestMethod.POST)
	public ModelAndView game(){
		ModelAndView view = new ModelAndView("admin/game");
		return view;
	}
	
	/** 礼包商城 **/
	@RequestMapping(value="mall", method=RequestMethod.POST)
	public ModelAndView mall(){
		ModelAndView view = new ModelAndView("admin/mall");
		return view;
	}
	
	/** 活动管理 **/
	@RequestMapping(value="activities", method=RequestMethod.POST)
	public ModelAndView activities(){
		ModelAndView view = new ModelAndView("admin/activities");
		return view;
	}
	
	/** 新闻公告 **/
	@RequestMapping(value="news", method=RequestMethod.POST)
	public ModelAndView news(){
		ModelAndView view = new ModelAndView("admin/news");
		return view;
	}
	
	/** 消息管理 **/
	@RequestMapping(value="message", method=RequestMethod.POST)
	public ModelAndView message(){
		ModelAndView view = new ModelAndView("admin/message");
		return view;
	}
	
	/** 日志管理 **/
	@RequestMapping(value="log", method=RequestMethod.POST)
	public ModelAndView log(){
		ModelAndView view = new ModelAndView("admin/log");
		return view;
	}
}
