package service.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.client.OrdersBean;

import common.utils.Def;

import dao.client.OrdersDao;

/*
 * Excel表
 */
@Controller
@RequestMapping("/excel")
public class ExcelService {
		
	/** 导出订单列表 */
	@RequestMapping(value ="exportOrders",method=RequestMethod.GET)
	@ResponseBody
	public void exportOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		List<OrdersBean> orderList = OrdersDao.loadAllOrder();
		
		if (orderList.size() == 0) {
			return;
		}
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("订单列表");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow(0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
		
        //HSSFCell cell;
        
        row.createCell(0).setCellValue("订单ID");  
        row.createCell(1).setCellValue("订单支付ID");  
        row.createCell(2).setCellValue("用户ID");  
        row.createCell(3).setCellValue("店铺ID");  
        row.createCell(4).setCellValue("商品订单列表信息");  
        row.createCell(5).setCellValue("订单状态");  
        row.createCell(6).setCellValue("售后服务");  
        row.createCell(7).setCellValue("支付方式");  
        row.createCell(8).setCellValue("下单时间");  
        row.createCell(9).setCellValue("支付时间");  
        row.createCell(10).setCellValue("发货时间");  
        row.createCell(11).setCellValue("收货时间");  
        
		JSONObject obj = new JSONObject();
		for (int i = 0; i < orderList.size(); i++) {
			// 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
	        row = sheet.createRow(i+1);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue(orderList.get(i).getOrderId());  
            row.createCell(1).setCellValue(orderList.get(i).getPayId());  
            row.createCell(2).setCellValue(orderList.get(i).getUid());  
            row.createCell(3).setCellValue(orderList.get(i).getShopId());  
            row.createCell(4).setCellValue(orderList.get(i).getGoodsList());  
            row.createCell(5).setCellValue(orderList.get(i).getStatus());  
            row.createCell(6).setCellValue(orderList.get(i).getAfterSaleService());  
            row.createCell(7).setCellValue(orderList.get(i).getPayWay());  
            row.createCell(8).setCellValue(getTimeStr(orderList.get(i).getCreateTime()));  
            row.createCell(9).setCellValue(getTimeStr(orderList.get(i).getPayTime()));  
            row.createCell(10).setCellValue(getTimeStr(orderList.get(i).getDeliverTime()));  
            row.createCell(11).setCellValue(getTimeStr(orderList.get(i).getReceiveTime()));  
		}
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "导出订单列表成功");
		out.print(obj);
		
		 // 第六步，将文件存到指定位置  
        try {  
            FileOutputStream fout = new FileOutputStream("F:/order"+getTimeStr(System.currentTimeMillis())+".xls");  
            wb.write(fout);  
            fout.close();  
        } catch (Exception e) {  
        	System.err.println("导出订单表出现异常");
            e.printStackTrace();  
        }
		
		out.flush();
		out.close();
	}
	
	public String getTimeStr(long time) {
		return new SimpleDateFormat("yyyy-mm-dd").format(time);
	}
	
	    
}
