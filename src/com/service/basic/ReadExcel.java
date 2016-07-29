package service.basic;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * poi 解析Excel文件
 */
public class ReadExcel{
	
	/**
	 * 通过Excel读取CDK序列码
	 * @param fileName
	 * @return CDK合并码
	 */
	public static String readExcel4CDK(String fileName){
		String type = ".xls";	//判断是否是.xlsx格式
		if(fileName.endsWith("xlsx"))
			type = ".xlsx";
		try {
			InputStream input = new FileInputStream(fileName);	//建立输入流
			Workbook wb  = null;
			//根据文件格式(.xls或者.xlsx)来初始化
			if(type.endsWith(".xlsx"))
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			
			String CDK = "";
			Sheet sheet = wb.getSheetAt(0);		//获得第一个表单
			Iterator<Row> rows = sheet.rowIterator();	//获得第一个表单的迭代器
			while (rows.hasNext()) {
				Row row = rows.next();	//获得行数据
				Iterator<Cell> cells = row.cellIterator();	//获得第一行的迭代器
				//System.out.println("Row #" + row.getRowNum());	//获得行号从0开始
				while (cells.hasNext()) {
					Cell cell = cells.next();
					if(cell.getColumnIndex()!=0) continue;
					//System.out.println("Cell #" + cell.getColumnIndex());
					switch (cell.getCellType()) {	//根据cell中的类型来输出数据
					case HSSFCell.CELL_TYPE_NUMERIC:
						if(CDK==null || CDK==""){
							CDK = String.valueOf((int) cell.getNumericCellValue());
						}
						else CDK = CDK + "," +String.valueOf((int) cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						 if(CDK==null || CDK==""){
							 CDK = cell.getStringCellValue();
						 }
						 else CDK = CDK + "," +cell.getStringCellValue();
						break;
					default:
						System.out.println("序列号格式不对！！！");
					break;
					}
				}
				
			}
			return CDK;
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	
}