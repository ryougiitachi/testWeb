package per.itachi.test.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(TestingFilter.class);
	
	public TestingFilter() {
		logger.debug("Filter {} has constructed. ", getClass().getSimpleName());
//		System.out.println("Filter {0} has constructed. ");
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.debug("Filter {} has initialised. ", getClass().getSimpleName());
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		logger.debug("Request {} has come into filter {}. ", req, getClass().getSimpleName());
//		System.out.println("Request {0} has come into filter {1}. ");
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		logger.debug("Filter {} has destroyed. ", getClass().getSimpleName());
	}
}
