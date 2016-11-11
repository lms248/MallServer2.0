package common.boot;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import main.service.admin.AdminUserService;
import common.config.Config;
import common.logger.LoggerManager;
import common.utils.TimerManagerUtils;

/**
 * 服务配置
 */
public class Server implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//回写日志
		LoggerManager.stopFileWriter();
		//清理定时器
		TimerManagerUtils.destroyed();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//初始化配置
		initConfig(arg0);
		//初始化日志配置
		LoggerManager.initLoggerConfig(Config.LOGGER_CONFIG);
		//初始化权限表
		AdminUserService.initAuthContent();
		//初始化用户数据
		AdminUserService.initUserContent();
		//初始化角色数据
		AdminUserService.initRoleContent();
		//初始化权限数据
		AdminUserService.initPermissionContent();
		//初始化主页导航
		AdminUserService.initNavigationContent();
		//初始化用户组数据
		AdminUserService.initGroupContent();
	}

	private void initConfig(ServletContextEvent sce){
		Config.init(sce);
	}
}
