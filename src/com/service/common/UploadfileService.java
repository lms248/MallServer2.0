package service.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.utils.HttpUtils;
import common.utils.ImageUtils;

/**
 * 文件上传业务
 */
@Controller
@RequestMapping("/service/upload2")
public class UploadfileService {

	/** 上传文件到本地服务器 */
	public String uploadfile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String savePath = "";
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
        try {
            fileList = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            return "";
        }
        Iterator<FileItem> it = fileList.iterator();
        String name = "";//上传后文件名
        String extName = "";//后缀名
        while (it.hasNext()) {
            FileItem item = it.next();
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
                    extName = name.substring(name.lastIndexOf("."));
                }
                
                String folder = "file";
                if(extName.toLowerCase().equals(".jpg") 
                	|| extName.toLowerCase().equals(".jpeg") 
                	|| extName.toLowerCase().equals(".png") 
                	|| extName.toLowerCase().equals(".gif")){  
                	folder = "image";
                }
                else if(extName.toLowerCase().equals(".xlsx") || extName.toLowerCase().equals(".xls")){
                	folder = "excel";
                }
                else {
                	folder = "file";
                }
                
                savePath = request.getSession().getServletContext().getRealPath("/upload/"+folder);
                
                File f1 = new File(savePath);
                System.out.println(savePath);
                if (!f1.exists()) {
                    f1.mkdirs();
                }
                
                //生成文件名：
                name = UUID.randomUUID().toString().replaceAll("-", "");
                File saveFile = new File(savePath+ "/" + name + extName);
                
                try {
                    item.write(saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return (name + extName);
            }
        }
		return (name + extName);
	}
	
	/** 上传图片进行压缩并存储到本地服务器 */
	public String uploadAndCompressImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return "";
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";//上传后文件名
		String extName = "";//后缀名
		String savePath = "";//存储路径
		InputStream imageInputStream = null;//图片上传的数据流
		
		int proportion = ImageUtils.PROPORTION_OLD;
		int width = 200;
		int height = 200;
		
		Date now = new Date(); //new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");///设置日期格式
		String time = df.format(now);
		
		while (it.hasNext()) {
			FileItem item = it.next();
			
			/* 获取传递过来的参数值 */
			String paramName = item.getFieldName();
			String paramValue = item.getString();
			if (paramName!=null) {
				switch (paramName) {
				case "proportion":
					try {
						if (Integer.parseInt(paramValue)==ImageUtils.PROPORTION_INPUT) proportion = ImageUtils.PROPORTION_INPUT;
						else if (Integer.parseInt(paramValue)==ImageUtils.PROPORTION_RATE) proportion = ImageUtils.PROPORTION_RATE;
					} catch (Exception e) {
						proportion = ImageUtils.PROPORTION_OLD;
					}
					System.out.println("proportion=="+proportion);
					break;
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
					extName = name.substring(name.lastIndexOf("."));
				}
				
				String folder = "file";
				if(extName.toLowerCase().equals(".jpg") 
						|| extName.toLowerCase().equals(".jpeg") 
						|| extName.toLowerCase().equals(".png") 
						|| extName.toLowerCase().equals(".gif")){  
					folder = "image";
				}
				else if(extName.toLowerCase().equals(".xlsx") || extName.toLowerCase().equals(".xls")){
					folder = "excel";
				}
				else {
					folder = "file";
				}
				
				savePath = request.getSession().getServletContext().getRealPath("/upload/"+folder+"/"+time);
				imageInputStream = item.getInputStream();
			}
			
		}
		
		//生成文件名：
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + extName;
		System.err.println("width="+width+";height="+height+";proportion="+proportion);
		
		File file = new File(savePath);
        if (!file.exists()) {
        	file.mkdirs();//创建文件目录
        }
        
		ImageUtils imageUtils = new ImageUtils();
		int success = imageUtils.compress(
				imageInputStream, savePath, fileName, width, height, proportion);
		
		System.out.println("###savePath==="+savePath);
		System.out.println("###fileName==="+fileName);
		System.out.println("###success==="+success);
		
		return (time+"/"+fileName);
	}
	
	/** 上传图片进行压缩并存储到本地服务器 */
	public String uploadAndCompressImage(HttpServletRequest request, HttpServletResponse response, 
			int width, int height, int proportion) throws ServletException, IOException{
		String savePath = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return "";
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";//上传后文件名
		String extName = "";//后缀名
		while (it.hasNext()) {
			FileItem item = it.next();
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
					extName = name.substring(name.lastIndexOf("."));
				}
				
				String folder = "file";
				if(extName.toLowerCase().equals(".jpg") 
						|| extName.toLowerCase().equals(".jpeg") 
						|| extName.toLowerCase().equals(".png") 
						|| extName.toLowerCase().equals(".gif")){  
					folder = "image";
				}
				else if(extName.toLowerCase().equals(".xlsx") || extName.toLowerCase().equals(".xls")){
					folder = "excel";
				}
				else {
					folder = "file";
				}
				
				savePath = request.getSession().getServletContext().getRealPath("/upload/"+folder);
				
				//生成文件名：
				String fileName = UUID.randomUUID().toString().replaceAll("-", "") + extName;
				
				ImageUtils imageUtils = new ImageUtils();
				imageUtils.compress(
						item.getInputStream(), savePath, fileName, width, height, proportion);
				
				return (fileName);
			}
		}
		return (name + extName);
	}
	
	
	/** 上传文件到ftp服务器 */
	public String uploadfile2FTP(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String savePath = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return "";
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";//上传后文件名
		String extName = "";//后缀名
		while (it.hasNext()) {
			FileItem item = it.next();
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
					extName = name.substring(name.lastIndexOf("."));
				}
				
				String ftpFolder = "fileCache/temp";
				String folder = "file";
				if(extName.toLowerCase().equals(".jpg") 
	                || extName.toLowerCase().equals(".jpeg") 
	                || extName.toLowerCase().equals(".png") 
	                || extName.toLowerCase().equals(".gif")){  
					ftpFolder = "/images/upload";
					folder = "image";
				}
				
				//生成文件名：
				name = UUID.randomUUID().toString().replaceAll("-", "");
				
				//上传文件到ftp
				boolean success = uploadFile2FTP("rsync.file.cachecn.net", 21,
						"cdn.thgame.com.cn", "6essVBbei/w2", ftpFolder, 
						name+extName,item.getInputStream());
				
				savePath = request.getSession().getServletContext().getRealPath("/upload/"+folder);
                
				//上传到本地服务器
                File f1 = new File(savePath);
                System.out.println(savePath);
                if (!f1.exists()) {
                    f1.mkdirs();
                }
                
                File saveFile = new File(savePath+ "/" + name + extName);
                
                try {
                    item.write(saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
				
				Date now = new Date(); //new Date()为获取当前系统时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");///设置日期格式
				String time = df.format(now);
				System.out.println(time+" 上传情况："+success);
				
				return (name + extName);
			}
		}
		return (name + extName);
	}
	
	/** 上传图片文件 */
	@RequestMapping(value ="image",method=RequestMethod.POST)
	@ResponseBody
	public void image(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String filename = uploadfile(request, response);
		out.print(filename);
		
		out.flush();
		out.close();
	}
	
	/** 上传图片文件,按原长宽大小进行压缩 */
	@RequestMapping(value ="image4compress",method=RequestMethod.POST)
	@ResponseBody
	public void image4compress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String filename = uploadAndCompressImage(request, response);
		out.print(filename);
		
		out.flush();
		out.close();
	}
	
	/** 上传图片文件,并生成缩略图 */
	@RequestMapping(value ="imageAndThumbnail",method=RequestMethod.POST)
	@ResponseBody
	public void imageAndThumbnail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		/*读取客户端提交的json数据*/
		JSONObject req_obj = HttpUtils.getJson4Stream(request.getInputStream());
		String width = req_obj.getString("width");
		System.out.println("width====="+width);
		String filename = uploadAndCompressImage(request, response);
		out.print(filename);
		
		out.flush();
		out.close();
	}
	
	
	
	/** 上传cdk数据文件 */
	@RequestMapping(value ="cdk",method=RequestMethod.POST)
	@ResponseBody
	public void cdk(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String filename = uploadfile(request, response);
		String savePath = request.getSession().getServletContext().getRealPath("/upload/excel");
		out.print(ReadExcel.readExcel4CDK(savePath+ "/" + filename));
		
		out.flush();
		out.close();
	}
	
	
	/** 
     * Description: 向FTP服务器上传文件 
     * @Version      1.0 
     * @param url FTP服务器hostname 
     * @param port  FTP服务器端口 
     * @param username FTP登录账号 
     * @param password  FTP登录密码 
     * @param filepath  FTP服务器保存目录 
     * @param filename  上传到FTP服务器上的文件名 
     * @param is   输入流 
     * @return 成功返回true，否则返回false * 
     */  
    public boolean uploadFile2FTP(String url,// FTP服务器hostname  
            int port,// FTP服务器端口  
            String username, // FTP登录账号  
            String password, // FTP登录密码  
            String filepath, // FTP服务器保存目录  
            String filename, // 上传到FTP服务器上的文件名  
            InputStream inputStream //输入文件流
    ){  
        boolean success = false;  
        FTPClient ftpClient = new FTPClient();  
        ftpClient.setControlEncoding("UTF-8");  
        try {  
            int reply;  
            ftpClient.connect(url, port);// 连接FTP服务器  
            // 如果采用默认端口，可以使用ftpClient.connect(url)的方式直接连接FTP服务器  
            ftpClient.login(username, password);// 登录  -
            reply = ftpClient.getReplyCode();  
            if (!FTPReply.isPositiveCompletion(reply)) {  
                ftpClient.disconnect();  
                return success;  
            }  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.makeDirectory(filepath);  
            ftpClient.changeWorkingDirectory(filepath);// 设置上传目录
            //ftpClient.setBufferSize(1024);// 设置缓冲区大小
            ftpClient.enterLocalPassiveMode();
            success = ftpClient.storeFile(filename, inputStream);  
            inputStream.close();  
            ftpClient.logout();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect();  
                } catch (IOException ioe) {  
                }  
            }  
        }  
        return success;  
    }  

}
