package service.basic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import common.config.Config;
import common.utils.Def;
import common.utils.ImageUtils;
import common.utils.UuidUtils;

/**
 * 文件上传业务
 */
@Controller
@RequestMapping("/service/upload")
public class UploadService {
	
	private String name = ""; //上传的文件名
	private String fileName = ""; //保存的文件名
	private String suffix = ""; //后缀名
	private String folder_image = Config.WEB_BASE+"/upload/image"; //原图存储文件夹
	private String folder_thumb = Config.WEB_BASE+"/upload/thumb"; //缩略图存储文件夹
	private String savePath_image = ""; //原图存储路径
	private String savePath_thumb = ""; //缩略图存储路径
	private InputStream inputStream = null; //上传的数据流
	private InputStream inputStream_thumb = null; //上传的数据流
	
	private int width = 200; //原图宽度
	private int height = 200; //原图高度
	private boolean aspectRatio = false; //不保持原纵横比（原图）
	private int thumb_width = 50; //缩略图宽度
	private int thumb_height = 50; //缩略图高度
	private boolean thumb_aspectRatio = false; //不保持原纵横比（缩略图）
	
	private Uploader<UploadService> uploader;

	public void setUploadParams(HttpServletRequest request, List<FileItem> fileList, String time) throws IOException {
		for (FileItem item : fileList) {
			System.out.println("item.getSize()===="+item.getSize());
			/* 获取传递过来的参数值 */
			String paramName = item.getFieldName();
			String paramValue = item.getString();
			if (paramName!=null) {
				switch (paramName) {
				case "width":
					try {
						width = Integer.parseInt(paramValue);
					} catch (Exception e) {
						width = 200;
					}
					System.out.println("width=="+width);
					break;
				case "height":
					try {
						height = Integer.parseInt(paramValue);
					} catch (Exception e) {
						height = 200;
					}
					System.out.println("height=="+height);
					break;
				case "aspectRatio":
					try {
						if (Integer.parseInt(paramValue) == 1) {
							aspectRatio = true;
						}
					} catch (Exception e) {
						aspectRatio = false;
					}
					System.out.println("aspectRatio=="+aspectRatio);
					break;
				case "thumb_width":
					try {
						thumb_width = Integer.parseInt(paramValue);
					} catch (Exception e) {
						thumb_width = 200;
					}
					System.out.println("thumb_width=="+thumb_width);
					break;
				case "thumb_height":
					try {
						thumb_height = Integer.parseInt(paramValue);
					} catch (Exception e) {
						thumb_height = 200;
					}
					System.out.println("thumb_height=="+thumb_height);
					break;
				case "thumb_aspectRatio":
					try {
						if (Integer.parseInt(paramValue) == 1) {
							thumb_aspectRatio = true;
						}
					} catch (Exception e) {
						thumb_aspectRatio = false;
					}
					System.out.println("thumb_aspectRatio=="+thumb_aspectRatio);
					break;
				default:
					break;
				}
			}
			
			if (!item.isFormField()) {
				name = item.getName();
				long size = item.getSize();
				String type = item.getContentType();
				
				System.out.println(size + " " + type);
				
				if (name == null || name.trim().equals("")) {
					continue;
				}
				//扩展名格式：  
				if (name.lastIndexOf(".") >= 0) {
					suffix = name.substring(name.lastIndexOf("."));
				}
				
				savePath_image = request.getSession().getServletContext().getRealPath(folder_image+"/"+time);
				savePath_thumb = request.getSession().getServletContext().getRealPath(folder_thumb+"/"+time);
				inputStream = item.getInputStream();
				inputStream_thumb = item.getInputStream();
			}
		}
	}
	
	public void setUploadParams2(HttpServletRequest request, List<MultipartFile> fileList, String time) throws IOException {
		for (MultipartFile file : fileList) {
			System.out.println("file.getSize()===="+file.getSize());
			name = file.getName();
			//扩展名格式：  
			if (name.lastIndexOf(".") >= 0) {
				suffix = name.substring(name.lastIndexOf("."));
			}
			
			savePath_image = request.getSession().getServletContext().getRealPath(folder_image+"/"+time);
			savePath_thumb = request.getSession().getServletContext().getRealPath(folder_thumb+"/"+time);
			inputStream = file.getInputStream();
			inputStream_thumb = file.getInputStream();
		}
	}
	
	/** 上传图片文件,并进行压缩 */
	@RequestMapping(value ="imageOutJson",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject imageOutJson(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = new ArrayList<FileItem>();
		try {
			fileList = upload.parseRequest(request);
			System.out.println("fileList.size()===="+fileList.size());
		} catch (FileUploadException ex) {
			return null;
		}
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		setUploadParams(request, fileList, time);
		
		//生成文件名：
		//fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		fileName = UuidUtils.getUuid4MD5_16() + suffix;
		//fileName = MD5.encode(UUID.randomUUID().toString().replaceAll("-", ""), "utf-8") + suffix;
		System.err.println("width="+width+";height="+height+";aspectRatio="+aspectRatio);
		
		File file = new File(savePath_image);
        if (!file.exists()) {
        	file.mkdirs();//创建文件目录
        }
        //生成原图
        Thumbnails.of(inputStream)
        .size(width, height)
        .keepAspectRatio(aspectRatio)
        .toFile(new File(savePath_image+"/"+fileName));
        
		File file_thumb = new File(savePath_thumb);
        if (!file_thumb.exists()) {
        	file_thumb.mkdirs();//创建文件目录
        }
        //生成缩略图
        Thumbnails.of(inputStream_thumb)
        .size(thumb_width, thumb_height)
        .keepAspectRatio(thumb_aspectRatio)
        .toFile(new File(savePath_thumb+"/"+fileName));
		
		JSONObject obj_out = new JSONObject();
		JSONObject obj_data = new JSONObject();
		JSONArray arr_data = new JSONArray();
		obj_data.put("thumb", folder_thumb+"/"+time+"/"+fileName);
		obj_data.put("image", folder_image+"/"+time+"/"+fileName);
		arr_data.add(obj_data);
		obj_out.put("code", Def.CODE_SUCCESS);
		obj_out.put("msg", "上传图片成功");
		obj_out.put("data", arr_data.toString());
        
		System.out.println(obj_out.toString());
		return obj_out;
	}
	
	/** 上传图片文件,并进行压缩 */
	@RequestMapping(value ="imageOutStr",method=RequestMethod.POST)
	@ResponseBody
	public String imageOutStr(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return "";
		}
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		setUploadParams(request, fileList, time);
		
		//生成文件名：
		//fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		fileName = UuidUtils.getUuid4MD5_16() + suffix;
		System.err.println("width="+width+";height="+height+";aspectRatio="+aspectRatio);
		
		File file = new File(savePath_image);
        if (!file.exists()) {
        	file.mkdirs();//创建文件目录
        }
        //生成原图
        Thumbnails.of(inputStream)
        .size(width, height)
        .keepAspectRatio(aspectRatio)
        .toFile(new File(savePath_image+"/"+fileName));
        
        String folder_thumbnail = Config.WEB_BASE+"/upload/thumb/"+time;
		String savePath_thumbnail = request.getSession().getServletContext().getRealPath(folder_thumbnail);
		File file_thumb = new File(savePath_thumbnail);
        if (!file_thumb.exists()) {
        	file_thumb.mkdirs();//创建文件目录
        }
        //生成缩略图
        Thumbnails.of(inputStream_thumb)
        .size(thumb_width, thumb_height)
        .keepAspectRatio(thumb_aspectRatio)
        .toFile(new File(savePath_thumbnail+"/"+fileName));
        
		return (time+"/"+fileName);
	}
	
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
		
		//setUploadParams2(request, fileList, time);
		
		JSONObject obj_out = new JSONObject();
		JSONObject obj_data = new JSONObject();
		JSONArray arr_data = new JSONArray();
		
		for (MultipartFile file : fileList) {
			System.out.println(file.getName()+"::::"+file.getSize());
			name = file.getOriginalFilename();
			System.out.println("originalFilename===="+name);
			savePath_image = request.getSession().getServletContext().getRealPath(folder_image+"/"+time);
			savePath_thumb = request.getSession().getServletContext().getRealPath(folder_thumb+"/"+time);
			inputStream = file.getInputStream();
			inputStream_thumb = file.getInputStream();
			//生成文件名：
			//fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
			//扩展名格式：  
			if (name.lastIndexOf(".") >= 0) {
				suffix = name.substring(name.lastIndexOf("."));
			}
			fileName = UuidUtils.getUuid4MD5_16() + suffix;
			System.out.println("fileName===="+fileName);
			//fileName = MD5.encode(UUID.randomUUID().toString().replaceAll("-", ""), "utf-8") + suffix;
			System.err.println("width="+width+";height="+height+";aspectRatio="+aspectRatio);
			
			File file_image = new File(savePath_image);
	        if (!file_image.exists()) {
	        	file_image.mkdirs();//创建文件目录
	        }
	        //生成原图
	        Thumbnails.of(inputStream)
	        .size(width, height)
	        .keepAspectRatio(aspectRatio)
	        .toFile(new File(savePath_image+"/"+fileName));
	        
			File file_thumb = new File(savePath_thumb);
	        if (!file_thumb.exists()) {
	        	file_thumb.mkdirs();//创建文件目录
	        }
	        //生成缩略图
	        Thumbnails.of(inputStream_thumb)
	        .size(thumb_width, thumb_height)
	        .keepAspectRatio(thumb_aspectRatio)
	        .toFile(new File(savePath_thumb+"/"+fileName));
	        
	        obj_data.put("thumb", folder_thumb+"/"+time+"/"+fileName);
			obj_data.put("image", folder_image+"/"+time+"/"+fileName);
			arr_data.add(obj_data);
		}
		
		obj_out.put("code", Def.CODE_SUCCESS);
		obj_out.put("msg", "上传图片成功");
		obj_out.put("data", arr_data.toString());
        
		System.out.println(obj_out.toString());
		
		out.print(obj_out.toString());
		out.flush();
		out.close();
		
		return;
	}
	
	public static String uploadImage(List<MultipartFile> fileList, String imagePath, String thumbPath, 
			int thumbWidth, int thumbHeight, boolean thumbKeepAspectRatio) throws IOException {
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		imagePath += "/"+time;
		thumbPath += "/"+time;
		
		StringBuffer imageList = new StringBuffer();
		
		for (MultipartFile file : fileList) {
			System.out.println(file.getName()+"::::"+file.getSize());
			String name = file.getOriginalFilename();
			System.out.println("originalFilename===="+name);
			InputStream inputStream_image = file.getInputStream();
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
	        
	        imageList.append(time+"/"+fileName).append(",");
		}
		
		
		return imageList.toString();
	}
}