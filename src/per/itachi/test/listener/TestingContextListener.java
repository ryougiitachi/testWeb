package per.itachi.test.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(TestingContextListener.class);
	
	public TestingContextListener() {
		logger.debug("Listener {} has constructed. ", getClass().getSimpleName());
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		logger.debug("The class loader of context {} is {}", context, context.getClassLoader());
		logger.debug("The value of parameter {} is {}. ", "log4jConfigLocation", context.getInitParameter("log4jConfigLocation"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("Listener {} has destroyed. ", getClass().getSimpleName());
		//check jvm settings
		Runtime runtime = Runtime.getRuntime();
		logger.debug("After destroying context, the max memory Xmx is {}", runtime.maxMemory());
		logger.debug("After destroying context, the total memory(Xms) is {}", runtime.totalMemory());
		logger.debug("After destroying context, the free memory(Xms) is {}", runtime.freeMemory());
		unregisterDatabaseDriver(event);//unregister driver manually.
	}
	
	/**
	 * Tomcat6.0.24开始增加了内存泄漏检测，在关闭tomcat的时候如果不关闭连接池卸载数据库驱动将会报错，以c3p0为例：<br/>
	 * <br/>
	 * WARNING: The web application [testWeb] registered the JDBC driver [org.postgresql.Driver] but failed to unregister it 
	 * when the web application was stopped. To prevent a memory leak, the JDBC Driver has been forcibly unregistered.<br/>
	 * WARNING: The web application [testWeb] appears to have started a thread named [C3P0PooledConnectionPoolManager
	 * [identityToken->|]-HelperThread-#0] but has failed to stop it. This is very likely to create a memory leak. 
	 * Stack trace of thread: (stack segments information)<br/>
	 * <br/>
	 * 连接池和驱动必须都关闭，有一个不关闭都会报错，在ContextListener里面关闭是一个不错的选择，若用spring框架管理，则关闭连接池对象
	 * 会在context销毁之前执行。<br/>
	 * <br/>
	 * Tomcat6.0.24会在以下几种情况发生时认为内存泄漏并报错：<br/>
	 * 1.JDBC驱动注册未卸载<br/>
	 * 2.一些日志框架<br/>
	 * 3.ThreadLocal中的一些保有对象并未删除<br/>
	 * 4.没有主动停止已经启动的线程<br/>
	 * 官方对于内存泄漏处理的解释 https://wiki.apache.org/tomcat/MemoryLeakProtection <br/>
	 * 常用性能分析工具 http://www.csdn.net/article/2012-05-30/2806167 <br/>
	 * <br/>
	 * Tomcat中主要的几个内检测监听器配置在server.xml：<br/>
	 * &lt;Listener className="org.apache.catalina.core.JreMemoryLeakPreventionListener"/&gt; <br/>
	 * &lt;Listener className="org.apache.catalina.mbeans.GlobalResourcesLifecycleListener"/&gt; <br/>
	 * &lt;Listener className="org.apache.catalina.core.ThreadLocalLeakPreventionListener"/&gt; <br/>
	 * Stackoverflow上关于Tomcat在shutdown时候报内存泄漏的分析与方案
	 * https://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered <br/>
	 * */
	private void unregisterDatabaseDriver(ServletContextEvent event) {
//		DriverManager.deregisterDriver(DriverManager.getDriver(url));//if url is known
		Driver driver = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		logger.debug("The class loader of current thread is {}.", cl);
		while (drivers.hasMoreElements()) {
			driver = drivers.nextElement();
			logger.debug("The class loader of driver {} is {}.", driver, driver.getClass().getClassLoader());
			//判断是否是当前应用加载的驱动，避免了同容器的其他应用的驱动被误卸载
			if (driver.getClass().getClassLoader() == cl) {
				try {
					logger.info("Unregistering the database driver {}", driver);
					DriverManager.deregisterDriver(driver);
					logger.info("Successfully unegistered the database driver {}", driver);
				} 
				catch (SQLException e) {
					logger.error("Failed to unregister this driver", driver, e);
				}
			}
		}
	}
}
