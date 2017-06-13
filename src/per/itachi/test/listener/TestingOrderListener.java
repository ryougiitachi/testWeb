package per.itachi.test.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * test whether the order of configuration in web.xml has influence on the order of loading and executing.
 * */
public class TestingOrderListener implements ServletRequestListener {

	private static final Logger logger = LoggerFactory.getLogger(TestingOrderListener.class);
	
	public TestingOrderListener() {
		logger.debug("Listener {} has constructed. ", getClass().getSimpleName());
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		logger.debug("Request {} has initialised. ", event.getSource());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		logger.debug("Request {} has destroyed. ", event.getSource());
	}

}
