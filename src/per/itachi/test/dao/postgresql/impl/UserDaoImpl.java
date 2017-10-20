package per.itachi.test.dao.postgresql.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import per.itachi.test.dao.postgresql.UserDao;
import per.itachi.test.pojo.postgresql.User;

@Repository("defaultUserDao")
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public User load(Long id) {
		return getCurrentSession().load(User.class, id);
	}

	@Override
	public User get(Long id) {
		return getCurrentSession().get(User.class, id);
	}

	@Override
	public List<User> findAll() {
		Query<User> query = getCurrentSession().createQuery("from User u", User.class);//hql?
		List<User> list = query.list();
		return list;
	}

	@Override
	public void persist(User entity) {
		getCurrentSession().persist(entity);
	}

	@Override
	public Long save(User entity) {
		return (Long)getCurrentSession().save(entity);
	}

	@Override
	public void saveOrUpdate(User entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(Long id) {
//		getCurrentSession().delete(id);
	}

	@Override
	public void flush() {
		getCurrentSession().flush();
	}

}
