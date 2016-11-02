package service.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PostConstruct;
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

import bean.basic.MessageBean;
import bean.basic.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import dao.basic.MessageDao;
import dao.basic.UserDao;

/**
 * 消息
 */
@Controller
@RequestMapping("/message")
public class MessageService {
	
	@Autowired  
    private UserDao userDao;
	@Autowired  
	private MessageDao messageDao;
	
	private static MessageService messageService;
	
	/** 
     * 构造方法执行后调用 init() 
     */  
    @PostConstruct  
    public void init() {  
    	messageService = this;  
    	messageService.userDao = this.userDao;
    	messageService.messageDao = this.messageDao;
    }
	
	/** 消息列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		
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
		
		List<MessageBean> messageList = messageDao.loadByUid(user.getUid(), index, size);
		
		JSONArray arr = new JSONArray();
		for (MessageBean message : messageList) {
			arr.add(JSONObject.fromObject(JsonUtils.jsonFromObject(message)));
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "购物车列表");
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/**
	 * 添加消息
	 * @param uid 用户ID
	 * @param title 标题
	 * @param content 内容
	 */
	public static void addMessage(String uid, String title, String content) {
		MessageBean message = new MessageBean();
		String messageId = IdGen.get().nextId()+"";
		message.setMessageId(messageId);
		message.setUid(uid);
		message.setTitle(title);
		message.setContent(content);
		message.setCreateTime(System.currentTimeMillis());
		messageService.messageDao.save(message);
		System.out.println("添加消息成功");
	}
	
	/**
	 * 订单状态消息体
	 */
	public static String getOrderMessage(String orderId, int status) {
		String msg = "";
		switch (status) {
		case Def.ORDER_STATUS_NOPAY: //待付款
			msg = "您的订单："+orderId+"，已进入待付款状态。敬请关注查看";
			break;
		case Def.ORDER_STATUS_NOTDELIVER: //待发货
			msg = "您的订单："+orderId+"，已进入待发货状态。敬请关注查看";
			break;
		case Def.ORDER_STATUS_NOTRECEIVE: //待收货
			msg = "您的订单："+orderId+"，已进入待发货状态。敬请关注查看";
			break;
		case Def.ORDER_STATUS_RECEIVE: //已收货
			msg = "您的订单："+orderId+"，已确认收货。敬请关注查看";
			break;
		case Def.ORDER_STATUS_CANCEL: //已取消
			msg = "您的订单："+orderId+"，已取消。敬请关注查看";
			break;
		case Def.ORDER_STATUS_AFTERSALES: //申请售后
			msg = "您的订单："+orderId+"，已进入售后处理中。敬请关注后续处理结果";
			break;
		default:
			break;
		}
		return msg;
	}
}
