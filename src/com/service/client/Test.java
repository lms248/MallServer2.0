package service.client;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			//System.out.println(System.currentTimeMillis());
			Test test = new Test();
			System.out.println(test.newID("123"));
			Thread.sleep(1000);
		}

	}
	
	/** 
     * 获得编号 
     * @param module 模块前缀 
     * @return 
     */
    public String newID(String module) { 
        String strNum = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) ; 
        strNum = strNum.substring(0, strNum.length()-1) ; 
        return module + strNum ; 
    } 


}
