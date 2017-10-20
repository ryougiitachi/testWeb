package per.itachi.test.listener;

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
		logger.debug("The value of parameter {} is {}. ", "log4jConfigLocation", context.getInitParameter("log4jConfigLocation"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("Listener {} has constructed. ", getClass().getSimpleName());
	}
}
