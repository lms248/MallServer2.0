package service.basic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传
 */
@Controller
@RequestMapping("/file/upload")
public class FileUploadController {
	
	public JSONObject uploadFiles(HttpServletRequest request, HttpServletResponse response) {
		
		//转型为MultipartHttpServletRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//获得上传的文件（根据前台的name名称得到上传的文件）
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		List<MultipartFile> file = multiValueMap.get("clientFile");
		//MultipartFile file = multipartRequest.getFile("clientFile");
		
		if (!file.isEmpty()) {
			//TODO 对file进行处理
			System.out.println(file.get(0).getName()+"::::"+file.get(0).getSize());
		}
		
		return null;
	}
	
	public String uploadFile(@RequestParam("fileName") String fileName, 
			@RequestParam("clientFile") MultipartFile clientFile, HttpSession session) {
		if (!clientFile.isEmpty()) {
			//TODO 对file进行处理
			System.out.println(file.get(0).getName()+"::::"+file.get(0).getSize());
		}
	}
}
