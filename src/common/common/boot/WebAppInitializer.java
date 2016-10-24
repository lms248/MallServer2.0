package common.boot;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import common.config.SpringConfig;

/**
 * 工程启动时对Spring配置和过滤器进行初始化加载
 */
public class WebAppInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("[System INFO] WebAppInitializer begin.");
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringConfig.class);
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
		ServletRegistration.Dynamic defaultDispatcher = servletContext.addServlet("default", new DefaultServlet());
		defaultDispatcher.setLoadOnStartup(1);
		defaultDispatcher.addMapping("*.ico");
		defaultDispatcher.addMapping("*.html");
		defaultDispatcher.addMapping("/res/*");
		defaultDispatcher.addMapping("/upload/*");
		defaultDispatcher.addMapping("/download/*");
		
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		FilterRegistration.Dynamic filter1 = servletContext.addFilter("hiddenHttpMethodFilter", hiddenHttpMethodFilter);
		filter1.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "*.do");
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		FilterRegistration.Dynamic filter2 = servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
		filter2.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "*.do");
		
		System.out.println("[System INFO] WebAppInitializer completed.");
	}

}
