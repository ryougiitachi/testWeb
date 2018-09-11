package per.itachi.test.service.oracle.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;

import per.itachi.test.dao.oracle.UserDao;
import per.itachi.test.pojo.oracle.User;
import per.itachi.test.service.oracle.UserService;

@Service("oracleUserService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	/**
	 * 嵌套的bean在外部是不能访问的，如果@Resource指定了name在找不到的情况下会报错
	 * */
//	@Resource
//	@Autowired
	private HikariConfig testNestedBean;
	
	@Autowired(required=false)
	@Qualifier("oracleUserDao")
	private UserDao userDao;

	@Override
	public User getUserByID(int userID) {
		return userDao.getUserByID(userID);
	}

	@Override
	public List<User> getUsersBeforeInsertTime(LocalDateTime insertTime) {
		return userDao.getUsersBeforeInsertTime(insertTime);
	}

	@Override
	public List<User> getUsersAfterInsertTime(LocalDateTime insertTime) {
		return userDao.getUsersAfterInsertTime(insertTime);
	}

	@Override
	public int insertUser(User user) {
		LocalDateTime now = LocalDateTime.now();
		user.setInsertTime(now);
		user.setUpdateTime(now);
		user.setAccessTime(now);
		return userDao.insertUser(user);
	}
	
	/**
	 * It is just OK that the method has the same name as the field.
	 * */
	@Override
	public void testNestedBean() {
		logger.debug("The content of nested bean is {}", testNestedBean);
	}
	
}
