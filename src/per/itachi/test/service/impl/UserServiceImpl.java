package per.itachi.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import per.itachi.test.dao.postgresql.UserDao;
import per.itachi.test.pojo.postgresql.User;
import per.itachi.test.service.UserService;

@Service("defaultUserService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	@Qualifier("defaultUserDao")
	private UserDao userDao;

	@Override
	public User load(long id) {
		return userDao.load(id);
	}

	@Override
	public User get(long id) {
		return userDao.get(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void persist(User entity) {
		userDao.persist(entity);
	}

	@Override
	public long save(User entity) {
		return userDao.save(entity).longValue();
	}

	@Override
	public void saveOrUpdate(User entity) {
		userDao.saveOrUpdate(entity);
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
	}

	@Override
	public void flush() {
		userDao.flush();
	}

}
