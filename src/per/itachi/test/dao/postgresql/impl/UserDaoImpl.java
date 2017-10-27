package per.itachi.test.dao.postgresql.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import per.itachi.test.dao.postgresql.UserDao;
import per.itachi.test.pojo.postgresql.User;

@Repository("defaultUserDao")
@Transactional(rollbackFor={Exception.class})
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * The method SessionFactory.getCurrentSession can be userd for <i>session-per-request</i> strategy.<br/>
	 * The purpose of doing like this can be to remove transaction demarcation code from the data access code, 
	 * which can avoid coding some explicit code, such as begin(), commit(), rollback(when exception occurs?) etc.<br/>
	 * */
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
	
	/**
	 * 它写起来灵活直观，而且与所熟悉的SQL的语法差不太多。条件查询、分页查询、连接查询、嵌套查询，
	 * 写起来与SQL语法基本一致，唯一不同的就是把表名换成了类或者对象。
	 * 其它的，包括一些查询函数 （count(),sum()等）、查询条件的设定等，全都跟SQL语法一样。
	 * */
	@Override
	public List<User> findUsersByHQL() {
		Query<User> query = getCurrentSession().createQuery("from User u", User.class);//hql?
		List<User> list = query.list();
		return list;
	}
	
	/**
	 * From Hibernate 5.2 on, SharedSessionContract.createCriteria() has been @Deprecated .
	 * Hibernate还有离线查询、复合查询、分页查询
	 * 这种方式比较面向对象方式，重点是有三个描述条件的对象：Restrictions,Order,Projections。使用QBC查询，一般需要以下三个步骤：
	 * 使用Session实例 的createCriteria()方法创建Criteria对象
	 * 使用工具类Restrictions的方法为Criteria对象设置查询条件，Order工具类的方法设置排序方式，Projections工具类的方法进行统计和分组。
	 * 使用Criteria对象的list()方法进行查询并返回结果
	 * */
	@Deprecated
	@Override
	public User findUserByQBC(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		Criterion criterion = Restrictions.eq(User.PROPERTY_USER_ID, id);
		criteria.add(criterion);
		List<?> listResult = criteria.list();
		return listResult.size() >0 ? (User)listResult.get(0) : null;
	}

	/**
	 * From Hibernate 5.2 on, SharedSessionContract.createCriteria() has been @Deprecated, 
	 * so Example.create() may become also deprecated(?)
	 * 将一个对象的非空属性作为查询条件进行查询。
	 * */
	@Deprecated
	@Override
	public User findUserByQBE(Long id) {
		User user = new User();
		user.setUserID(id);
		Example example = Example.create(user);
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(example);
		List<?> listResult = criteria.list();
		return listResult.size() >0 ? (User)listResult.get(0) : null;
	}
	
	/**
	 * From Hibernate 5.2 on, It is recommanded to use JPA Criteria instead of Hibernate Criteria.
	 * */
	public List<User> findUsersByJPACriteria() {
		Session session = getCurrentSession();
		CriteriaBuilder criteria = session.getCriteriaBuilder();
		CriteriaQuery<User> query = criteria.createQuery(User.class);
		Root<User> root = query.from(User.class);
//		Predicate condition = criteria.lt
		return null;
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
