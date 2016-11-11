package main.service.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.bean.basic.ImageInfo;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import common.utils.Def;
import common.utils.StringUtils;
import common.utils.UuidUtils;

/**
 * 上传文件（不经过spring的multipart处理）
 */
@WebServlet("/servlet/upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = new ArrayList<FileItem>();
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		ImageInfo imageInfo = setUploadParams(request, fileList, new ImageInfo(), time);
		
		File file = new File(imageInfo.getSavePath());
        if (!file.exists()) {
        	file.mkdirs();//创建文件目录
        }
        //生成原图
        Thumbnails.of(imageInfo.getInputStream())
        .size(imageInfo.getWidth(), imageInfo.getHeight())
        .keepAspectRatio(imageInfo.isAspectRatio())
        .toFile(new File(imageInfo.getSavePath()+"/"+imageInfo.getFileName()));
        
		File file_thumb = new File(imageInfo.getSavePath_thumb());
        if (!file_thumb.exists()) {
        	file_thumb.mkdirs();//创建文件目录
        }
        //生成缩略图
        Thumbnails.of(imageInfo.getInputStream_thumb())
        .size(imageInfo.getWidth_thumb(), imageInfo.getHeight_thumb())
        .keepAspectRatio(imageInfo.isAspectRatio_thumb())
        .toFile(new File(imageInfo.getSavePath_thumb()+"/"+imageInfo.getFileName_thumb()));
		
		JSONObject obj_out = new JSONObject();
		JSONObject obj_data = new JSONObject();
		JSONArray arr_data = new JSONArray();
		obj_data.put("image", imageInfo.getFolder()+"/"+time+"/"+imageInfo.getFileName());
		obj_data.put("thumb", imageInfo.getFolder_thumb()+"/"+time+"/"+imageInfo.getFileName_thumb());
		arr_data.add(obj_data);
		obj_out.put("code", Def.CODE_SUCCESS);
		obj_out.put("msg", "上传图片成功");
		obj_out.put("data", arr_data.toString());
        
		System.out.println(obj_out.toString());
		
		String outStr = imageInfo.getFolder()+"/"+time+"/"+imageInfo.getFileName()
				+ ";" + imageInfo.getFolder_thumb()+"/"+time+"/"+imageInfo.getFileName_thumb();
		
		out.print(outStr);
		out.flush();
		out.close();
	}
	
	/**
	 * 设置图片信息
	 * 
	 * @param request
	 * @param fileList
	 * @param imageInfo
	 * @param time
	 * @return 
	 * @throws IOException
	 */
	public ImageInfo setUploadParams(HttpServletRequest request, List<FileItem> fileList, ImageInfo imageInfo, String time) throws IOException {
		for (FileItem item : fileList) {
			/* 获取传递过来的参数值 */
			String paramName = item.getFieldName();
			String paramValue = item.getString();
			if (paramName!=null) {
				switch (paramName) {
				case "width":
					try {
						imageInfo.setWidth(Integer.parseInt(paramValue));
					} catch (Exception e) {
						System.err.println("width数据错误");
						e.printStackTrace();
					}
					System.out.println("width=="+imageInfo.getWidth());
					break;
				case "height":
					try {
						imageInfo.setHeight(Integer.parseInt(paramValue));
					} catch (Exception e) {
						System.err.println("height数据错误");
						e.printStackTrace();
					}
					System.out.println("height=="+imageInfo.getHeight());
					break;
				case "aspectRatio":
					if (paramValue.equals("true")) {
						imageInfo.setAspectRatio(true);
					}
					System.out.println("aspectRatio=="+imageInfo.isAspectRatio());
					break;
				case "width_thumb":
					try {
						imageInfo.setWidth_thumb(Integer.parseInt(paramValue));
					} catch (Exception e) {
						System.err.println("width_thumb数据错误");
						e.printStackTrace();
					}
					System.out.println("width_thumb=="+imageInfo.getWidth_thumb());
					break;
				case "height_thumb":
					try {
						imageInfo.setHeight_thumb(Integer.parseInt(paramValue));
					} catch (Exception e) {
						System.err.println("height_thumb数据错误");
						e.printStackTrace();
					}
					System.out.println("height_thumb=="+imageInfo.getHeight_thumb());
					break;
				case "aspectRatio_thumb":
					if (paramValue.equals("true")) {
						imageInfo.setAspectRatio_thumb(true);
					}
					System.out.println("aspectRatio_thumb=="+imageInfo.isAspectRatio_thumb());
					break;
				default:
					break;
				}
			}
			
			if (!item.isFormField()) {
				imageInfo.setOriginName(item.getName());
				long size = item.getSize();
				String type = item.getContentType();
				
				System.out.println(size + " " + type);
				
				if (StringUtils.isBlank(imageInfo.getOriginName())) {
					continue;
				}
				//扩展名格式：  
				if (imageInfo.getOriginName().lastIndexOf(".") >= 0) {
					//后缀名
					String suffix = imageInfo.getOriginName().substring(imageInfo.getOriginName().lastIndexOf("."));
					imageInfo.setSuffix(suffix);
					//存储的文件名
					//String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
					String fileName = UuidUtils.getUuid4MD5_16() + suffix;
					imageInfo.setFileName(fileName);
					imageInfo.setFileName_thumb(fileName);
				}
				
				//数据流
				imageInfo.setSavePath(request.getSession().getServletContext().getRealPath(imageInfo.getFolder()+"/"+time));
				imageInfo.setSavePath_thumb(request.getSession().getServletContext().getRealPath(imageInfo.getFolder_thumb()+"/"+time));
				imageInfo.setInputStream(item.getInputStream());
				imageInfo.setInputStream_thumb(item.getInputStream());
			}
			
		}
		return imageInfo;
	}

}
