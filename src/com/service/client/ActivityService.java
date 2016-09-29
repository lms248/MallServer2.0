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
	
	/** 添加活动(弃用，改用edit) */
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
		String title = request.getParameter("title");
		String mark = request.getParameter("mark");
		
		JSONObject obj = new JSONObject();
		
		GoodsBean goods = new GoodsBean();
		try {
			goods = GoodsDao.loadByGoodsId(goodsId);
		} catch (Exception e) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String activityId = IdGen.get().nextId()+"";
		
		ActivityBean activity = new ActivityBean();
		activity.setActivityId(activityId);
		activity.setGoodsId(goodsId);
		activity.setSortId(Integer.parseInt(sortId));
		activity.setTitle(title);
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
	
	/** 活动编辑 */
	@RequestMapping(value ="edit",method=RequestMethod.POST)
	@ResponseBody
	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String editType = request.getParameter("editType");
		String goodsId = request.getParameter("goodsId");
		String sortId = request.getParameter("sortId");
		String title = request.getParameter("title");
		String mark = request.getParameter("mark");
		
		JSONObject obj = new JSONObject();
		
		GoodsBean goods = new GoodsBean();
		try {
			goods = GoodsDao.loadByGoodsId(goodsId);
		} catch (Exception e) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			return;
		}
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			return;
		}
		
		if (editType != null && editType.equals("add")) { //添加活动
			String activityId = IdGen.get().nextId()+"";
			
			ActivityBean activity = new ActivityBean();
			activity.setActivityId(activityId);
			activity.setGoodsId(goodsId);
			activity.setSortId(Integer.parseInt(sortId));
			activity.setTitle(title);
			activity.setMark(mark);
			activity.setCreateTime(System.currentTimeMillis());
			
			ActivityDao.save(activity);
			
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "添加活动成功");
			obj.put("data", JsonUtils.jsonFromObject(ActivityDao.loadByActivityId(activityId)));
			out.print(obj);
		} else { //修改活动
			if (request.getParameter("activityId") == null) {
				obj.put("code", Def.CODE_FAIL);
				obj.put("msg", "活动ID不正确");
				out.print(obj);
				return;
			}
			String activityId = request.getParameter("activityId");
			ActivityBean activity = ActivityDao.loadByActivityId(activityId);
			if (activity == null) {
				obj.put("code", Def.CODE_FAIL);
				obj.put("msg", "该活动不存在");
				out.print(obj);
				return;
			}
			activity.setGoodsId(goodsId);
			activity.setSortId(Integer.parseInt(sortId));
			activity.setTitle(title);
			activity.setMark(mark);
			
			ActivityDao.update(activity);
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "修改活动成功");
			obj.put("data", JsonUtils.jsonFromObject(ActivityDao.loadByActivityId(activityId)));
			out.print(obj);
		}
		
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
		
		String activityId = request.getParameter("activityId"); 
		
		JSONObject obj = new JSONObject();
		ActivityBean activity = ActivityDao.loadByActivityId(activityId);
		JSONObject obj_data = JSONObject.fromObject(activity);
		if (activity.getSortId() <= 0) {
			obj_data.put("sortIds", 0);
		} else {
			int pid = SortDao.loadById(activity.getSortId()).getPid();
			if (pid==0) {
				obj_data.put("sortIds", activity.getSortId());
			} else {
				obj_data.put("sortIds", pid+":"+activity.getSortId());
			}
		}
		
		obj_data.put("goodsId", activity.getGoodsId()+"");
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "活动信息");
		obj.put("data", JsonUtils.jsonFromObject(obj_data));
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
	
	/** 活动商品列表（弃用） */
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
			obj2.put("title", activityList.get(i).getTitle());
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
	
	/** 删除 */
	@RequestMapping(value ="delete",method=RequestMethod.POST)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("------------/activity/delete-------------");
		
		String activityId = request.getParameter("activityId");
		
		JSONObject obj = new JSONObject();
		
		ActivityBean activity = ActivityDao.loadByActivityId(activityId);
		
		int result = ActivityDao.deleteByActivityId(activity.getActivityId());
		if (result == -1) {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除活动失败");
			obj.put("data", "");
			out.print(obj);
		} else {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除活动成功");
			out.print(obj);
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
