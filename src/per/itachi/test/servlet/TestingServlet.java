package per.itachi.test.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7360971562211382531L;
	
	private static final Logger logger = LoggerFactory.getLogger(TestingServlet.class);
	
	public TestingServlet() {
		super();
		logger.debug("Servlet {} has constructed {}. ", getClass().getSimpleName(), logger.getClass().getName());
	}

	/**
	 * This initialisation method without parameter executes at first.
	 * */
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("Servlet {} has initialised. ", getClass().getSimpleName());
	}

	/**
	 * This initialisation method with parameter executes at second.
	 * */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger.debug("Servlet {} has initialised with {}. ", getClass().getSimpleName(), config);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		logger.debug("Servlet {} gets a new request {}. ", getClass().getSimpleName(), req);
		super.service(req, res);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//HTTP Status 405 - HTTP method GET is not supported by this URL
//		super.doGet(req, resp);
		logger.debug("Servlet {} gets a GET request {}. ", getClass().getSimpleName(), req);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//HTTP Status 405 - HTTP method PUT is not supported by this URL
//		super.doPut(req, resp);//super method shouldn't be executed.
		logger.debug("Servlet {} gets a PUT request {}. ", getClass().getSimpleName(), req);
	}

	@Override
	public void destroy() {
		super.destroy();
		logger.debug("Servlet {} has destroyed. ", getClass().getSimpleName());
	}

	@Override
	public void log(String message, Throwable t) {
		super.log(message, t);
	}

	@Override
	public void log(String msg) {
		super.log(msg);
	}
}
