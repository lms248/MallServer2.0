package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import bean.client.ActivityBean;
import bean.client.GoodsBean;
import bean.client.ShopBean;
import bean.client.SortBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import dao.client.ActivityDao;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import dao.client.SortDao;

/**
 * 活动
 */
@Controller
@RequestMapping("/activity")
public class ActivityService {
	
	/** 添加活动 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String goodsId = request.getParameter("goodsId");
		String sortId = request.getParameter("sortId");
		String mark = request.getParameter("mark");
		
		JSONObject obj = new JSONObject();
		
		GoodsBean goods = GoodsDao.loadByGoodsId(Long.parseLong(goodsId));
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		long activityId = IdGen.get().nextId();
		
		ActivityBean activity = new ActivityBean();
		activity.setActivityId(activityId);
		activity.setGoodsId(Long.parseLong(goodsId));
		activity.setSortId(Integer.parseInt(sortId));
		activity.setMark(mark);
		activity.setCreateTime(System.currentTimeMillis());
		
		ActivityDao.save(activity);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加活动成功");
		obj.put("data", JsonUtils.jsonFromObject(ActivityDao.loadByActivityId(activityId)));
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 活动信息 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		long activityId = Long.parseLong(request.getParameter("activityId")); 
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "活动信息");
		obj.put("data", JsonUtils.jsonFromObject(ActivityDao.loadByActivityId(activityId)));
		out.print(obj);
		
		out.flush();
		out.close();
	}
	
	/** 活动列表 */
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
		
		List<ActivityBean> activityList = ActivityDao.loadAllActivity(index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < activityList.size(); i++) {
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(activityList.get(i)));
			//转化成字符串类型
			obj2.put("activityId", ""+activityList.get(i).getActivityId());
			obj2.put("goodsId", ""+activityList.get(i).getGoodsId());
			GoodsBean goods = GoodsDao.loadByGoodsId(activityList.get(i).getGoodsId());
			if (goods != null) {
				obj2.put("goodsName", goods.getName());
			}
			obj2.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(activityList.get(i).getCreateTime())));
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "活动列表");
		obj.put("count", ActivityDao.Count());
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 活动商品列表 */
	@RequestMapping(value ="goodsInfoList",method=RequestMethod.GET)
	@ResponseBody
	public void goodsInfoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		String sortId = request.getParameter("sortId");//分类ID
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		
		List<ActivityBean> activityList = new ArrayList<ActivityBean>();
		List<SortBean> sortList = SortDao.loadByPid(Integer.parseInt(sortId));
		if (sortList.size() == 0) {//一类
			activityList = ActivityDao.loadActivityForSort(Integer.parseInt(sortId), -1, index, size);
		} else {
			activityList = ActivityDao.loadActivityForSort(-1, Integer.parseInt(sortId), index, size);
		}
		
		for (int i = 0; i < activityList.size(); i++) {
			GoodsBean goods = GoodsDao.loadByGoodsId(activityList.get(i).getGoodsId());
			if (goods == null) {
				continue;
			}
			ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
			if (shop == null) {
				continue;
			}
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(goods));
			//转化成字符串类型
			obj2.put("shopId", goods.getShopId());
			obj2.put("goodsId", goods.getGoodsId());
			obj2.put("shopName", shop.getName());
			obj2.put("shopLogo", shop.getImage());
			obj2.put("shopThumb", shop.getThumbnail());
			obj2.put("contactPhone", shop.getContactPhone());
			arr.add(obj2);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商品列表");
		obj.put("data", arr);
		out.print(obj);
		
		System.out.println(obj);

		
		out.flush();
		out.close();
	}
}
