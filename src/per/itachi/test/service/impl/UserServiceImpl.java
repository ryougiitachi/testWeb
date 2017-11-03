package per.itachi.test.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
		Timestamp insertDate = Timestamp.valueOf(LocalDateTime.now());
		entity.setInsertDate(insertDate);
		return userDao.save(entity).longValue();
	}
	
	@Override
	public void update(User entity) {
		Timestamp insertDate = Timestamp.valueOf(LocalDateTime.now());
		entity.setInsertDate(insertDate);
		userDao.update(entity);
	}

	@Override
	public void saveOrUpdate(User entity) {
		Timestamp insertDate = Timestamp.valueOf(LocalDateTime.now());
		entity.setInsertDate(insertDate);
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
