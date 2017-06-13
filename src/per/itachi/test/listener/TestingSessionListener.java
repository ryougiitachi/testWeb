package per.itachi.test.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingSessionListener implements HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(TestingSessionListener.class);
	
	private int countOfSession;
	
	private Object mutexSession;
	
	public TestingSessionListener() {
		countOfSession = 0;
		mutexSession = new Object();
		logger.debug("Listener {} has constructed. ", getClass().getSimpleName());
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.debug("A new session {} comes in. ", event.getSession().getId());
		synchronized (mutexSession) {
			++countOfSession;
//			logger.debug("A new session comes in, the current number of session is {}", countOfSession);
		}
		//If out of synchronised block, it may appear that both 2 different sessions output 25(assumption)
		//But into synchronised block, it is serial processing with less efficiency.
		//It all depends.
		logger.debug("After creating, the current number of session is {}", countOfSession);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpsessionevent) {
		logger.debug("A new session {} is gone. ", httpsessionevent.getSession().getId());
		synchronized (mutexSession) {
			--countOfSession;//need to check whether count is less than 0?
		}
		logger.debug("After destroying, the current number of session is {}", countOfSession);
	}
}
