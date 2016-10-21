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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import bean.client.ActivityBean;
import bean.client.GoodsBean;
import bean.client.ShopBean;
import bean.client.SortBean;
import bean.client.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;
import dao.client.ActivityDao;
import dao.client.CollectDao;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import dao.client.SortDao;
import dao.client.UserDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商品
 */
@Controller
@RequestMapping("/goods")
public class GoodsService {
	
	/** 添加商品 */
	@RequestMapping(value ="add",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("-----goods:::add------");
		
		String name = request.getParameter("name");
		String shopId = request.getParameter("shopId");
		String curPrice = request.getParameter("curPrice");
		String prePrice = request.getParameter("prePrice");
		String stock = request.getParameter("stock");
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String logo = request.getParameter("logo");
		String imageList = request.getParameter("imageList");
		String thumbList = request.getParameter("thumbList");
		String[] logos = logo.split(";");
		
		String sortId = request.getParameter("sortId");
		
		System.out.println("shopId===="+shopId);
		if (ShopDao.loadByShopId(shopId) == null) {
			JSONObject obj = new JSONObject();
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商店名不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		
		String[] tag = tags.split(";");
		JSONObject tagObj = new JSONObject();
		for (String t : tag) {
			if (StringUtils.isBlank(t)) {
				break;
			}
			String[] tt = t.split(":");
			if (tt.length < 2) {
				break;
			}
			tagObj.put(tt[0], JSON.toJSONString(tt[1].split("#")));
		}
		
		String goodsId = IdGen.get().nextId()+"";
		
		GoodsBean goods = new GoodsBean();
		goods.setGoodsId(goodsId);
		goods.setShopId(shopId);
		goods.setCurPrice(Double.parseDouble(curPrice));
		goods.setPrePrice(Double.parseDouble(prePrice));
		goods.setStock(Integer.parseInt(stock));
		goods.setTags(tagObj.toString());
		goods.setName(name);
		goods.setTitle(title);
		goods.setDetails(details);
		goods.setSortId(Integer.parseInt(sortId));
		goods.setLogo(logos[0]);
		goods.setLogoThumb(logos[1]);
		goods.setImageList(JSON.toJSONString(imageList.split(",")));
		goods.setThumbList(JSON.toJSONString(thumbList.split(",")));
		goods.setCreateTime(System.currentTimeMillis());
		
		GoodsDao.save(goods);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "添加商品成功");
		obj.put("data", JsonUtils.jsonFromObject(GoodsDao.loadByGoodsId(goodsId)));
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 修改商品 */
	@RequestMapping(value ="update",method=RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("-----goods:::update------");
		
		String goodsId = request.getParameter("goodsId");
		String name = request.getParameter("name");
		String curPrice = request.getParameter("curPrice");
		String prePrice = request.getParameter("prePrice");
		String stock = request.getParameter("stock");
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String logo = request.getParameter("logo");
		String imageList = request.getParameter("imageList");
		String thumbList = request.getParameter("thumbList");
		String[] logos = logo.split(";");
		
		String sortId = request.getParameter("sortId");
		
		GoodsBean goods = GoodsDao.loadByGoodsId(goodsId);
		
		if (goods == null) {
			JSONObject obj = new JSONObject();
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品(goodsId:"+goodsId+")不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String[] tag = tags.split(";");
		JSONObject tagObj = new JSONObject();
		for (String t : tag) {
			if (StringUtils.isBlank(t)) {
				break;
			}
			String[] tt = t.split(":");
			if (tt.length < 2) {
				break;
			}
			tagObj.put(tt[0], JSON.toJSONString(tt[1].split("#")));
		}
		
		goods.setCurPrice(Double.parseDouble(curPrice));
		goods.setPrePrice(Double.parseDouble(prePrice));
		goods.setStock(Integer.parseInt(stock));
		goods.setTags(tagObj.toString());
		goods.setName(name);
		goods.setTitle(title);
		goods.setDetails(details);
		goods.setSortId(Integer.parseInt(sortId));
		goods.setLogo(logos[0]);
		goods.setLogoThumb(logos[1]);
		goods.setImageList(JSON.toJSONString(imageList.split(",")));
		goods.setThumbList(JSON.toJSONString(thumbList.split(",")));
		
		GoodsDao.update(goods);
		
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "修改商品成功");
		obj.put("data", JsonUtils.jsonFromObject(goods.getGoodsId()));
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 商品详情 */
	@RequestMapping(value ="info",method=RequestMethod.GET)
	@ResponseBody
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String goodsId = request.getParameter("goodsId"); 
		
		JSONObject obj = new JSONObject();
		
		UserBean user = UserDao.loadByToken(token);
		
		GoodsBean goods = GoodsDao.loadByGoodsId(goodsId);
		
		JSONObject obj_data = JSONObject.fromObject(goods);
		ShopBean shop = ShopDao.loadByShopId(goods.getShopId());
		if (shop != null) {
			obj_data.put("shopName", shop.getName());
			obj_data.put("shopLogo", shop.getImage());
			obj_data.put("shopThumb", shop.getThumbnail());
			obj_data.put("contactPhone", shop.getContactPhone());
		}
		
		if (goods.getSortId() <= 0) {
			obj_data.put("sortIds", 0);
		} else {
			int pid = SortDao.loadById(goods.getSortId()).getPid();
			if (pid == 0) {
				obj_data.put("sortIds", goods.getSortId());
			} else {
				obj_data.put("sortIds", pid+":"+goods.getSortId());
			}
		}
		
		int isCollect = 0;//是否已收藏，0否，1是
		if (user != null) {
			if (CollectDao.loadByUidAndGoodId(user.getUid(), goods.getGoodsId()) != null) {
				isCollect = 1;
			}
		}
		
		obj_data.put("isCollect", isCollect);
		obj_data.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(goods.getCreateTime())));
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商品详情");
		obj.put("data", JsonUtils.jsonFromObject(obj_data));
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 商品列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		String shopId = request.getParameter("shopId");//商店ID
		String sortId = request.getParameter("sortId");//分类ID
		String type = request.getParameter("type");//类别,1是普通商品,2是活动商品
		
		//商品查询属性,用于搜索
		String searchContent = request.getParameter("searchContent");//搜索内容
		if (StringUtils.isBlank(searchContent)) {
			searchContent = "";
		} 
		
		JSONObject obj = new JSONObject();
		UserBean user = UserDao.loadByToken(token);
		
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		
		if (StringUtils.isBlank(type) || type.equals("1")) {
			List<GoodsBean> goodsList = new ArrayList<GoodsBean>();
			if (shopId == null) { //不分店铺
				if (sortId == null || sortId.equals("0")) { //不分类
					goodsList = GoodsDao.loadAllGoods_search(searchContent, index, size);
				} else { //分类
					List<SortBean> sortList = SortDao.loadByPid(Integer.parseInt(sortId));
					if (sortList.size() == 0) {//一类
						goodsList = GoodsDao.loadAllGoodsForSort_search(Integer.parseInt(sortId), -1, searchContent, index, size);
					} else {
						goodsList = GoodsDao.loadAllGoodsForSort_search(-1, Integer.parseInt(sortId), searchContent, index, size);
					}
				}
			} else { //分店铺
				if (sortId == null || sortId.equals("0")) { //不分类
					goodsList = GoodsDao.loadAllGoodsForShop_search(Long.parseLong(shopId), searchContent, index, size);
				} else { //分类
					List<SortBean> sortList = SortDao.loadByPid(Integer.parseInt(sortId));
					if (sortList.size() == 0) {//一类
						goodsList = GoodsDao.loadAllGoodsForShopAndSort_search(Long.parseLong(shopId), Integer.parseInt(sortId), -1, searchContent, index, size);
					} else {
						goodsList = GoodsDao.loadAllGoodsForShopAndSort_search(Long.parseLong(shopId), -1, Integer.parseInt(sortId), searchContent, index, size);
					}
				}
			}
			
			for (int i = 0; i < goodsList.size(); i++) {
				ShopBean shop = ShopDao.loadByShopId(goodsList.get(i).getShopId());
				if (shop == null) {
					continue;
				}
				obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(goodsList.get(i)));
				
				int isCollect = 0;//是否已收藏，0否，1是
				
				if (user != null) {
					if (CollectDao.loadByUidAndGoodId(user.getUid(), goodsList.get(i).getGoodsId()) != null) {
						isCollect = 1;
					}
				}
				
				//转化成字符串类型
				obj2.put("isCollect", isCollect);
				obj2.put("shopName", shop.getName());
				obj2.put("shopLogo", shop.getImage());
				obj2.put("shopThumb", shop.getThumbnail());
				obj2.put("contactPhone", shop.getContactPhone());
				obj2.put("createTime2", ""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(goodsList.get(i).getCreateTime())));
				arr.add(obj2);
			}
			obj.put("count", GoodsDao.Count());
		} else if (type.equals("2")) {
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
				
				int isCollect = 0;//是否已收藏，0否，1是
				if (user != null) {
					if (CollectDao.loadByUidAndGoodId(user.getUid(), goods.getGoodsId()) != null) {
						isCollect = 1;
					}
				}
				
				obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(goods));
				obj2.put("title", activityList.get(i).getTitle());
				obj2.put("shopId", goods.getShopId());
				obj2.put("goodsId", goods.getGoodsId());
				obj2.put("isCollect", isCollect);
				obj2.put("shopName", shop.getName());
				obj2.put("shopLogo", shop.getImage());
				obj2.put("shopThumb", shop.getThumbnail());
				obj2.put("contactPhone", shop.getContactPhone());
				arr.add(obj2);
			}
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
		
		System.out.println("------------/goods/delete-------------");
		
		String goodsId = request.getParameter("goodsId");
		
		JSONObject obj = new JSONObject();
		
		GoodsBean goods = GoodsDao.loadByGoodsId(goodsId);
		
		int result = GoodsDao.deleteByGoodsId(goods.getGoodsId());
		if (result == -1) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "删除商品失败");
			obj.put("data", "");
			out.print(obj);
		} else {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除商品成功");
			obj.put("data", "");
			out.print(obj);
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
