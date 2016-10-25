package service.client;

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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.PayBean;
import bean.client.UserBean;
import common.utils.Def;
import common.utils.JsonUtils;
import dao.client.PayDao;
import dao.client.UserDao;

/**
 * 支付订单
 */
@Controller
@RequestMapping("/pay")
public class PayService {
	
	/** 支付订单列表 */
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
		String status = request.getParameter("status");//订单状态
		
		List<PayBean> payList = PayDao.loadPayByStatus(index, size, Integer.parseInt(status));
		
		JSONObject obj = new JSONObject();
		JSONObject outObj = new JSONObject();
		JSONArray arr = new JSONArray();
		for (PayBean pay : payList) {
			outObj = JSONObject.fromObject(JsonUtils.jsonFromObject(pay));
			UserBean user = UserDao.loadByUid(pay.getUid());
			outObj.put("username", ""+user==null?"":user.getUsername());
			outObj.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(pay.getCreateTime())));
			outObj.put("payTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(pay.getPayTime())));
			arr.add(outObj);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "支付订单列表");
		obj.put("count", PayDao.Count());
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
}
