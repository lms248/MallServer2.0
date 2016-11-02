package service.client;

import java.io.IOException;
import java.io.PrintWriter;
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

import bean.client.CommentBean;
import bean.client.GoodsBean;
import bean.client.UserBean;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.JsonUtils;
import common.utils.StringUtils;
import dao.mybatis.CommentDao;
import dao.mybatis.GoodsDao;
import dao.mybatis.UserDao;

/**
 * 商品评论
 */
@Controller
@RequestMapping("/comment")
public class CommentService {
	
	@Autowired  
    private UserDao userDao;
	@Autowired  
	private GoodsDao goodsDao;
	@Autowired  
	private CommentDao commentDao;
	
	/** 发表评论 */
	@RequestMapping(value ="publish",method=RequestMethod.POST)
	@ResponseBody
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String token = request.getParameter("token");
		String goodsId = request.getParameter("goodsId");
		String orderId = request.getParameter("orderId");
		String content = request.getParameter("content");
		String stars = request.getParameter("star");

		JSONObject obj = new JSONObject();
		
		if (StringUtils.isBlank(token) || StringUtils.isBlank(goodsId) || StringUtils.isBlank(orderId)
				|| StringUtils.isBlank(content) || StringUtils.isBlank(stars)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不正确");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		int star = Integer.parseInt(stars);
		if (star < 1) {
			star = 1;
		} else if (star > 5) {
			star = 5;
		}
		
		UserBean user = userDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		GoodsBean goods = goodsDao.loadByGoodsId(goodsId);
		if (goods == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "该商品不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String commentId = IdGen.get().nextId()+"";
		CommentBean comment = new CommentBean();
		comment.setCommentId(commentId);
		comment.setGoodsId(goodsId);
		comment.setOrderId(orderId);
		comment.setUid(user.getUid());
		comment.setContent(content);
		comment.setStar(star);
		comment.setCreateTime(System.currentTimeMillis());
		commentDao.save(comment);
		
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "发表评论成功");
		obj.put("data", JsonUtils.jsonFromObject(comment));
		out.print(obj);
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
	
	/** 评论列表 */
	@RequestMapping(value ="infoList",method=RequestMethod.GET)
	@ResponseBody
	public void infoList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String goodsId = request.getParameter("goodsId");//商品ID
		int index = Integer.parseInt(request.getParameter("index"));//索引开始
		int size = Integer.parseInt(request.getParameter("size"));//条数
		
		List<CommentBean> commentList = commentDao.loadByGoodsId(goodsId, index, size);
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < commentList.size(); i++) {
			obj2 = JSONObject.fromObject(commentList.get(i));
			UserBean user = userDao.loadByUid(commentList.get(i).getUid());
			if (user == null) {
				continue;
			}
			obj2.put("nickname", user.getNickname());
			obj2.put("avatar", user.getAvatar());
			obj2.put("thumbnail", user.getThumbnail());
			arr.add(obj2);
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "评论列表");
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
		
		System.out.println("------------/comment/delete-------------");
		
		String token = request.getParameter("token");
		String commentId = request.getParameter("commentId");
		
		JSONObject obj = new JSONObject();
		
		if (StringUtils.isBlank(token) || StringUtils.isBlank(commentId)) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "参数不正确");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		UserBean user = userDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		int result = commentDao.deleteByCommentId(commentId);
		if (result == -1) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "删除评论失败");
			obj.put("data", "");
			out.print(obj);
		} else {
			obj.put("code", Def.CODE_SUCCESS);
			obj.put("msg", "删除评论成功");
			out.print(obj);
		}
		
		System.out.println(obj);
		
		out.flush();
		out.close();
	}
}
