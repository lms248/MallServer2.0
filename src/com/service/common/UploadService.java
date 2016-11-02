package service.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import bean.basic.ImageInfo;
import common.utils.Def;
import common.utils.UuidUtils;

/**
 * 文件上传业务
 */
@Controller
@RequestMapping("/service/upload")
public class UploadService {
	
	/**
	 * MultipartFile 请求形式上传图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value ="image",method=RequestMethod.POST)
	public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//转型为MultipartHttpServletRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//获得上传的文件（根据前端的name名称得到上传的文件）
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		System.out.println("--------------------------uploadImage--------------------------");
		System.out.println(multiValueMap);
		System.out.println("---------------------------------------------------------------");
		List<MultipartFile> fileList = multiValueMap.get("file");
		//MultipartFile file = multipartRequest.getFile("clientFile");
		
		if (fileList.isEmpty()) {
			return;
		}
		
		System.out.println("fileList.size()===="+fileList.size());
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		ImageInfo imageInfo = new ImageInfo();
		
		List<String> imageList = new ArrayList<String>();
		List<String> thumbList = new ArrayList<String>();
		
		for (MultipartFile file : fileList) {
			System.out.println(file.getName()+"::::"+file.getSize());
			String name = file.getOriginalFilename();
			System.out.println("originalFilename===="+name);
			String savePath_image = request.getSession().getServletContext().getRealPath(imageInfo.getFolder()+"/"+time);
			String savePath_thumb = request.getSession().getServletContext().getRealPath(imageInfo.getFolder_thumb()+"/"+time);
			InputStream inputStream = file.getInputStream();
			//生成文件名：
			//fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
			//扩展名格式：  
			String suffix = ".jpg";
			if (name.lastIndexOf(".") >= 0) {
				suffix = name.substring(name.lastIndexOf("."));
			}
			String fileName = UuidUtils.getUuid4MD5_16() + suffix;
			System.out.println("fileName===="+fileName);
			//fileName = MD5.encode(UUID.randomUUID().toString().replaceAll("-", ""), "utf-8") + suffix;
			
			File file_image = new File(savePath_image);
	        if (!file_image.exists()) {
	        	file_image.mkdirs();//创建文件目录
	        }
	        //生成原图
	        file.transferTo(new File(savePath_image+"/"+fileName));
	        
			File file_thumb = new File(savePath_thumb);
	        if (!file_thumb.exists()) {
	        	file_thumb.mkdirs();//创建文件目录
	        }
	        //生成缩略图
	        Thumbnails.of(inputStream)
	        .size(imageInfo.getWidth_thumb(), imageInfo.getHeight_thumb())
	        .keepAspectRatio(imageInfo.isAspectRatio_thumb())
	        .toFile(new File(savePath_thumb+"/"+fileName));
	        
	        imageList.add("/upload/image/"+time+"/"+fileName);
	        thumbList.add("/upload/thumb/"+time+"/"+fileName);
	        
		}
		
		JSONObject obj_out = new JSONObject();
		obj_out.put("code", Def.CODE_SUCCESS);
		obj_out.put("msg", "上传图片成功");
		obj_out.put("data", obj_out.toString());
        
		System.out.println(obj_out.toString());
		
		out.print(obj_out.toString());
		out.flush();
		out.close();
		
	}
	
	/**
	 * MultipartFile 形式存储图片，保存原图和缩略图
	 * 
	 * @param fileList
	 * @param imagePath
	 * @param thumbPath
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbKeepAspectRatio
	 * @return
	 * @throws IOException
	 */
	public static JSONObject uploadImage(List<MultipartFile> fileList, String imagePath, String thumbPath, 
			int thumbWidth, int thumbHeight, boolean thumbKeepAspectRatio) throws IOException {
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		imagePath += "/"+time;
		thumbPath += "/"+time;
		
		List<String> imageList = new ArrayList<String>();
		List<String> thumbList = new ArrayList<String>();
		
		for (MultipartFile file : fileList) {
			System.out.println(file.getName()+"::::"+file.getSize());
			String name = file.getOriginalFilename();
			System.out.println("originalFilename===="+name);
			InputStream inputStream_thumb = file.getInputStream();
			
			String suffix = ".jpg";
			//扩展名格式：  
			if (name.lastIndexOf(".") >= 0) {
				suffix = name.substring(name.lastIndexOf("."));
			}
			//生成文件名：
			String fileName = UuidUtils.getUuid4MD5_16() + suffix;
			System.out.println("fileName===="+fileName);
			
			File file_image = new File(imagePath);
	        if (!file_image.exists()) {
	        	file_image.mkdirs();//创建文件目录
	        }
	        // 保存原图
	        file.transferTo(new File(imagePath+"/"+fileName));
	        
			File file_thumb = new File(thumbPath);
	        if (!file_thumb.exists()) {
	        	file_thumb.mkdirs();//创建文件目录
	        }
	        //生成缩略图
	        Thumbnails.of(inputStream_thumb)
	        .size(thumbWidth, thumbHeight)
	        .keepAspectRatio(thumbKeepAspectRatio)
	        .toFile(new File(thumbPath+"/"+fileName));
	        
	        imageList.add("/upload/image/"+time+"/"+fileName);
	        thumbList.add("/upload/thumb/"+time+"/"+fileName);
		}
		JSONObject obj = new JSONObject();
		obj.put("imageList", imageList.toArray());
		obj.put("thumbList", thumbList.toArray());
		return obj;
	}
}
