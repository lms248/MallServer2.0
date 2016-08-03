package service.basic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.utils.ImageUtils;

/**
 * 文件上传业务
 */
@Controller
@RequestMapping("/service/upload")
public class UploadService {
	
	private String name = ""; //上传的文件名
	private String fileName = ""; //保存的文件名
	private String suffix = ""; //后缀名
	private String savePath = ""; //存储路径
	private InputStream inputStream = null; //上传的数据流
	
	private int width = 200; //原图宽度
	private int height = 200; //原图高度
	private int proportion = ImageUtils.PROPORTION_OLD; //原图缩放类型
	private int thumb_width = 50; //缩略图宽度
	private int thumb_height = 50; //缩略图高度
	private int thumb_proportion = ImageUtils.PROPORTION_OLD; //缩略图缩放类型
	
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
				case "proportion":
					try {
						proportion = Integer.parseInt(paramValue);
					} catch (Exception e) {
						proportion = ImageUtils.PROPORTION_OLD;
					}
					System.out.println("proportion=="+proportion);
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
				case "thumb_proportion":
					try {
						thumb_proportion = Integer.parseInt(paramValue);
					} catch (Exception e) {
						thumb_proportion = ImageUtils.PROPORTION_OLD;
					}
					System.out.println("thumb_proportion=="+thumb_proportion);
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
				
				String folder = "/upload/image/"+time;
				savePath = request.getSession().getServletContext().getRealPath(folder);
				inputStream = item.getInputStream();
			}
		}
	}
	
	/** 上传图片文件,并进行压缩 */
	@RequestMapping(value ="image",method=RequestMethod.POST)
	@ResponseBody
	public String image(HttpServletRequest request, HttpServletResponse response) 
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
		fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		System.err.println("width="+width+";height="+height+";proportion="+proportion);
		
		File file = new File(savePath);
        if (!file.exists()) {
        	file.mkdirs();//创建文件目录
        }
        
		ImageUtils imageUtils = new ImageUtils();
		int success = imageUtils.compress(
				inputStream, savePath, fileName, width, height, proportion);
		
		//原图
		/*TimerManagerUtils.scheduleOne(() -> {
			String folder = "/upload/image/"+time;
			savePath = request.getSession().getServletContext().getRealPath(folder);
			int success = imageUtils.compress(inputStream, savePath, fileName, width, height, proportion);
			System.out.println("image###success==="+success);
		}, 0, TimeUnit.SECONDS);*/
		
		//缩略图
		/*TimerManagerUtils.scheduleOne(() -> {
			String folder = "/upload/thumbnail/"+time;
			savePath = request.getSession().getServletContext().getRealPath(folder);
			int success = imageUtils.compress(inputStream, savePath, fileName, thumb_width, thumb_height, thumb_proportion);
			System.out.println("thumbnail###success==="+success);
		}, 0, TimeUnit.SECONDS);*/
		
		System.out.println("###savePath==="+savePath);
		System.out.println("###fileName==="+fileName);
		System.out.println("###success==="+success);
		
		return (time+"/"+fileName);
	}

}
