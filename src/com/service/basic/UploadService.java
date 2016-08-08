package service.basic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.config.Config;
import common.utils.Def;
import common.utils.MD5;
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
	

	/*public UploadService(int width, int height, int proportion,
			int thumb_width, int thumb_height, int thumb_proportion,
			Uploader<UploadService> uploader) {
		super();
		this.width = width;
		this.height = height;
		this.proportion = proportion;
		this.thumb_width = thumb_width;
		this.thumb_height = thumb_height;
		this.thumb_proportion = thumb_proportion;
		this.uploader = uploader;
	}*/

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
				
				folder_image = folder_image+"/"+time;
				folder_thumb = folder_thumb+"/"+time;
				savePath_image = request.getSession().getServletContext().getRealPath(folder_image);
				savePath_thumb = request.getSession().getServletContext().getRealPath(folder_thumb);
				inputStream = item.getInputStream();
				inputStream_thumb = item.getInputStream();
			}
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
		fileName = MD5.encode(UUID.randomUUID().toString().replaceAll("-", ""), "utf-8") + suffix;
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
        
		savePath_thumb = request.getSession().getServletContext().getRealPath(folder_thumb);
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
		obj_data.put("thumb", folder_thumb);
		obj_data.put("image", folder_image);
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
}
