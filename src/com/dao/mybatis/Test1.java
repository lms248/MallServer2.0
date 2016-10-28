package dao.mybatis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import common.config.Config;
import bean.client.UserBean;

public class Test1 {
	
	public static void main(String[] args) throws IOException {
        //mybatis的配置文件
        String resource = Config.ROOT_DIR + File.separator+"WebContent"+File.separator+"WEB-INF" 
        		+ File.separator + Config.CONFIG_DIR + File.separator + Config.MYBATIS_CONFIG;
        System.out.println("resource===="+resource);
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();  
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader); 
        SqlSession session = sqlSessionFactory.openSession();
        
        UserDao userDao = session.getMapper(UserDao.class);  
        UserBean user = userDao.loadById(1);  
        System.out.println(user.toString());
    }
	
}
