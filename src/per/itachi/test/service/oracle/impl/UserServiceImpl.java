package per.itachi.test.service.oracle.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import per.itachi.test.dao.oracle.UserDao;
import per.itachi.test.pojo.oracle.User;
import per.itachi.test.service.oracle.UserService;

@Service("oracleUserService")
public class UserServiceImpl implements UserService {
	
	@Autowired
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
}
