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
	
	/**
	 * 有可能会出现这个错误could not initialize proxy - no Session，
	 * 因为Session.load出来的是一个代理对象，没有发sql语句<br/>
	 * <br/>
	 * 通过在mapping文件里将class设为lazy=false(默认为true)可以解决此问题，但这种方式会把所有内容加载出来，一般不推荐<br/>
	 * <br/>
	 * 可以通过在web.xml添加org.springframework.orm.hibernate5.support.OpenSessionInViewFilter解决，此时hibernate将不再管理事务，
	 * spring管理session开关问题，只有当request过来得时候该filter获取一个session直到request结束session被释放
	 * 这种方式符合"Open Session in View"<br/>
	 * */
	@Override
	public User load(Long id) {
		return getCurrentSession().load(User.class, id);
	}

	/**
	 * 发送了sql语句，从数据库中查询后放到缓存中
	 * */
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
