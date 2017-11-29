package per.itachi.test.interceptor.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TestHandlerInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(TestHandlerInterceptor.class);
	
	public TestHandlerInterceptor() {
		logger.debug("Spring HandlerInterceptor, namely {}, has initialised.", getClass().getSimpleName());
	}
	
	/**
	 * 返回true时正常执行，返回false时DispatcherServlet将不执行。
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("The interceptor {} in preHandle, with {}, {}, {}.", getClass().getSimpleName(), request, response, handler);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mvc)
			throws Exception {
		logger.debug("The interceptor {} in postHandle, with {}, {}, {}, {}.", getClass().getSimpleName(), request, response, handler, mvc);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("The interceptor {} in afterCompletion, with {}, {}, {}, {}.", getClass().getSimpleName(), request, response, handler, ex);
	}
}
