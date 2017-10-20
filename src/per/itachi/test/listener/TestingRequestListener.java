package per.itachi.test.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For Servlet 3.0, it is not necessary to add listener to web.xml. 
 * Instead, it is just okay to mark annotation @WebListener.
 * */
public class TestingRequestListener implements ServletRequestListener {
	
	private static final Logger logger = LoggerFactory.getLogger(TestingRequestListener.class);

	public TestingRequestListener() {
		logger.debug("Listener {} has constructed, using {}. ", getClass().getSimpleName(), logger);
//		System.out.println("Listener {0} has constructed. ");
	}
	
	@Override
	public void requestInitialized(ServletRequestEvent event) {
		logger.debug("Request {} has initialised. ", event.getServletRequest());
//		System.out.println("Request {0} has initialised. ");
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		logger.debug("Request {} has destroyed. ", event.getServletRequest());
	}
}
