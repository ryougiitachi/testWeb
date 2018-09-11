package per.itachi.test.interceptor.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class TestWebRequestInterceptor implements WebRequestInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(TestWebRequestInterceptor.class);
	
	public TestWebRequestInterceptor() {
		logger.debug("Spring WebRequestInterceptor, namely {}, has initialised.", getClass().getSimpleName());
	}

	@Override
	public void preHandle(WebRequest request) throws Exception {
		logger.debug("The interceptor {} has intercepted a request {}.", getClass().getSimpleName(), request);
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		logger.debug("DispatcherServlet has returned result {} to {}.", model, getClass().getSimpleName());
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		logger.debug("The interceptor {} has completed {} with {}.", getClass().getSimpleName(), request, ex);
	}
}
