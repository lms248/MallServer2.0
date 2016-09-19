package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import bean.client.GoodsBean;
import bean.client.ShopBean;
import bean.client.SortBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;
import dao.client.GoodsDao;
import dao.client.ShopDao;
import dao.client.SortDao;
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
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String logo = request.getParameter("logo");
		String imageList = request.getParameter("imageList");
		String thumbList = request.getParameter("thumbList");
		String[] logos = logo.split(";");
		
		String sortId = request.getParameter("sortId");
		
		System.out.println("shopId===="+shopId);
		if (ShopDao.loadByShopId(Long.parseLong(shopId)) == null) {
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
		
		long goodsId = IdGen.get().nextId();
		
		GoodsBean goods = new GoodsBean();
		goods.setGoodsId(goodsId);
		goods.setShopId(Long.parseLong(shopId));
		goods.setCurPrice(Double.parseDouble(curPrice));
		goods.setPrePrice(Double.parseDouble(prePrice));
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
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		String logo = request.getParameter("logo");
		String imageList = request.getParameter("imageList");
		String thumbList = request.getParameter("thumbList");
		String[] logos = logo.split(";");
		
		String sortId = request.getParameter("sortId");
		
		GoodsBean goods = GoodsDao.loadByGoodsId(Long.parseLong(goodsId));
		
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
		
		long goodsId = Long.parseLong(request.getParameter("goodsId")); 
		
		GoodsBean goods = GoodsDao.loadByGoodsId(goodsId);
		
		JSONObject obj = new JSONObject();
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
			obj_data.put("sortIds", pid+":"+goods.getSortId());
		}
		
		obj_data.put("goodsId", goods.getGoodsId()+"");
		
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
		
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		String shopId = request.getParameter("shopId");//商店ID
		String sortId = request.getParameter("sortId");//分类ID
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		
		List<GoodsBean> goodsList = new ArrayList<GoodsBean>();
		if (shopId == null) { //不分店铺
			if (sortId == null || sortId.equals("0")) { //不分类
				goodsList = GoodsDao.loadAllGoods(index, size);
			} else { //分类
				List<SortBean> sortList = SortDao.loadByPid(Integer.parseInt(sortId));
				if (sortList.size() == 0) {//一类
					goodsList = GoodsDao.loadAllGoodsForSort(Integer.parseInt(sortId), -1, index, size);
				} else {
					goodsList = GoodsDao.loadAllGoodsForSort(-1, Integer.parseInt(sortId), index, size);
				}
			}
		} else { //分店铺
			if (sortId == null || sortId.equals("0")) { //不分类
				goodsList = GoodsDao.loadAllGoodsForShop(Long.parseLong(shopId), index, size);
			} else { //分类
				List<SortBean> sortList = SortDao.loadByPid(Integer.parseInt(sortId));
				if (sortList.size() == 0) {//一类
					goodsList = GoodsDao.loadAllGoodsForShopAndSort(Long.parseLong(shopId), Integer.parseInt(sortId), -1, index, size);
				} else {
					goodsList = GoodsDao.loadAllGoodsForShopAndSort(Long.parseLong(shopId), -1, Integer.parseInt(sortId), index, size);
				}
			}
		}
		
		for (int i = 0; i < goodsList.size(); i++) {
			ShopBean shop = ShopDao.loadByShopId(goodsList.get(i).getShopId());
			if (shop == null) {
				continue;
			}
			obj2 = JSONObject.fromObject(JsonUtils.jsonFromObject(goodsList.get(i)));
			//转化成字符串类型
			obj2.put("shopId", ""+goodsList.get(i).getShopId());
			obj2.put("goodsId", ""+goodsList.get(i).getGoodsId());
			obj2.put("shopName", shop.getName());
			obj2.put("shopLogo", shop.getImage());
			obj2.put("shopThumb", shop.getThumbnail());
			obj2.put("contactPhone", shop.getContactPhone());
			arr.add(obj2);
		}
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "商品列表");
		obj.put("data", arr);
		obj.put("count", GoodsDao.Count());
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
		
		GoodsBean goods = GoodsDao.loadByGoodsId(Long.parseLong(goodsId));
		
		int result = GoodsDao.deleteByGoods(goods.getGoodsId());
		if (result == -1) {
			obj.put("code", Def.CODE_SUCCESS);
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
