package per.itachi.test.dao.postgresql.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
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
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import per.itachi.test.dao.postgresql.UserDao;
import per.itachi.test.pojo.postgresql.User;

@Repository("defaultUserDao")
@Transactional(rollbackFor={Exception.class})
public class UserDaoImpl implements UserDao {
	
	private static final String SQL_GET_ALL_USERS = 
			"SELECT USER_ID, USERNAME, USER_TYPE, INSERT_DATE, UPDATE_TIME FROM T_USER ORDER BY USER_ID";

	private static final String NAMED_QUERY_GET_ALL_USERS = "HQL_GET_ALL_USERS";
	private static final String NAMED_QUERY_GET_USER_BY_ID = "HQL_GET_USER_BY_ID";
	
	private static final String NAMED_NATIVE_QUERY_GET_ALL_USERS = "SQL_GET_ALL_USERS";
	private static final String NAMED_NATIVE_QUERY_GET_USER_BY_ID = "SQL_GET_USER_BY_ID";

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
	 * 用load方式查询在找不到数据时会抛以下异常：
	 * org.hibernate.ObjectNotFoundException: No row with the given identifier exists: [per.itachi.test.pojo.postgresql.User#100]
	 * */
	@Override
	public User load(Long id) {
		return getCurrentSession().load(User.class, id);
	}

	/**
	 * 发送了sql语句，从数据库中查询后放到缓存中
	 * 用get方式查询在找不到数据时只是会返回null而非像load一样抛异常
	 * 
	 * */
	@Override
	public User get(Long id) {
		return getCurrentSession().get(User.class, id);
	}

	@Override
	public List<User> findAll() {
		List<User> result = null;
//		result = findUsersByHQL();						//OK
//		result = findUsersByJPACriteria();				//OK
//		result = findUsersByNativeQuery();				//OK
		result = findUsersByNamedQuery();				//OK
//		result = findUsersByNamedNativeQuery();			//not OK
		return result;
	}
	
	/**
	 * 它写起来灵活直观，而且与所熟悉的SQL的语法差不太多。条件查询、分页查询、连接查询、嵌套查询，
	 * 写起来与SQL语法基本一致，唯一不同的就是把表名换成了类或者对象。
	 * 其它的，包括一些查询函数 （count(),sum()等）、查询条件的设定等，全都跟SQL语法一样。
	 * 关键字大小写不敏感，但是类名、属性名、定义的alias同义词大小写敏感，混淆的话将导致异常，例如写成这样：
	 * from User u ORDER BY U.userID 
	 * org.hibernate.hql.internal.ast.QuerySyntaxException: 
	 * Invalid path: 'U.userID' [from per.itachi.test.pojo.postgresql.User u ORDER BY U.userID]
	 * */
	@Override
	public List<User> findUsersByHQL() {
		Query<User> query = getCurrentSession().createQuery("from User u ORDER BY u.userID", User.class);//hql?
		List<User> list = query.list();
		return list;
	}
	
	/**
	 * From Hibernate 5.2 on, It is recommended to use JPA Criteria instead of Hibernate Criteria.
	 * */
	@Override
	public List<User> findUsersByJPACriteria() {
		Session session = getCurrentSession();
		CriteriaBuilder criteria = session.getCriteriaBuilder();
		CriteriaQuery<User> query = criteria.createQuery(User.class);
		//SELECT ... FROM
		Root<User> root = query.from(User.class);
		root.alias("u");
		//Before using the following code, there must be a User constructor with columns listed in select list.
		//Otherwise, exception will occur as follows: 
		//org.hibernate.hql.internal.ast.QuerySyntaxException: Unable to locate appropriate constructor on class 
		//[per.itachi.test.pojo.postgresql.User]. Expected arguments are: long, java.lang.String, int, java.util.Date, 
		//java.time.LocalDateTime [select new per.itachi.test.pojo.postgresql.User(u.userID, u.username, u.userType, 
		//u.insertDate, u.updateTime) from per.itachi.test.pojo.postgresql.User as u where ( 1=1 ) and ( u.insertDate<=:param0 )]
//		query.multiselect(root.get(User.PROPERTY_USER_ID).alias(User.PROPERTY_USER_ID));//also available
//		query.multiselect(root.get(User.PROPERTY_USER_ID), 
//				root.get(User.PROPERTY_USERNAME), 
//				root.get(User.PROPERTY_USER_TYPE), 
//				root.get(User.PROPERTY_INSERT_DATE), 
//				root.get(User.PROPERTY_UPDATE_TIME));
		//WHERE
		Predicate where = null;
		where = criteria.conjunction();
		where = criteria.and(where, criteria.lessThanOrEqualTo(
				root.<Timestamp>get(User.PROPERTY_INSERT_DATE), Timestamp.valueOf(LocalDateTime.now())));
		query.where(where);
		//ORDER BY 
		query.orderBy(criteria.asc(root.get(User.PROPERTY_USER_ID)));
		//execute
		List<User> listResult = session.createQuery(query).getResultList();
		return listResult;
	}
	
	/**
	 * NativeQuery can be used for SQL query. 
	 * Since Hibernate 5.2, Hibernate recommends that use NativeQuery to replace SQLQuery. 
	 * SQLQuery is deprecated.
	 * */
	@Override
	public List<User> findUsersByNativeQuery() {
		Session session = getCurrentSession();
		NativeQuery<User> query = session.createNativeQuery(SQL_GET_ALL_USERS, User.class);
		return query.getResultList();
	}
	
	@Override
	public List<User> findUsersByNamedQuery() {
		Session session = getCurrentSession();
		Query<User> query = session.createNamedQuery(NAMED_QUERY_GET_ALL_USERS, User.class);
		return query.getResultList();
	}
	
	/**
	 * Query.iterate is deprecated since Hibernate 5.2 because of deprecation of org.hibernate.Query
	 * */
	@Deprecated
	@Override
	public Iterator<User> findUsersByIterate() {
		Query<User> query = getCurrentSession().createQuery("from User u ORDER BY u.userID", User.class);//hql?
		Iterator<User> list = query.iterate();
		return list;
	}
	
	/**
	 * 不能返回泛型，如果不在配置文件里面指定类型就会返回一个Object[]类型元素的，list中的第一个元素是代理类型lazy，不知道为什么，存疑
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByNamedNativeQuery() {
		Session session = getCurrentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(NAMED_NATIVE_QUERY_GET_ALL_USERS);
		return (List<User>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByNamedNativeQuery(Long id) {
		Session session = getCurrentSession();
		NativeQuery<?> query = session.getNamedNativeQuery(NAMED_NATIVE_QUERY_GET_USER_BY_ID);
		query.setParameter(User.COLUMN_USER_ID, id);
		return (List<User>)query.getResultList();
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
		user.setUserID(id);//不知道为什么这个条件不起作用
		Example example = Example.create(user);
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		criteria.add(example);
		List<?> listResult = criteria.list();
		return listResult.size() >0 ? (User)listResult.get(0) : null;
	}
	
	/**
	 * Query.getSingleResult()如果结果数不只有一个将抛出以下异常：
	 * org.hibernate.NonUniqueResultException: query did not return a unique result: 7
	 * */
	@Override
	public User findUserByNamedQuery(Long id) {
		Session session = getCurrentSession();
		Query<User> query = null;
//		query = session.createNamedQuery(NAMED_QUERY_GET_ALL_USERS, User.class);
		query = session.createNamedQuery(NAMED_QUERY_GET_USER_BY_ID, User.class);
		query.setParameter(User.PROPERTY_USER_ID, id);
		return query.getSingleResult();
	}

	/**
	 * 该方法提供对JPA写法的照顾，不像save方法，persist不返回主键，该方法在事务外不会立即执行(?)，可用于封装长会话流程。
	 * */
	@Override
	public void persist(User entity) {
		getCurrentSession().persist(entity);
	}

	/**
	 * 调用后立即执行并返回
	 * */
	@Override
	public Long save(User entity) {
		return (Long)getCurrentSession().save(entity);
	}
	
	/**
	 * Hibernate的Session.update方法如果没有数据更新会抛异常
	 * org.hibernate.StaleStateException: Batch update returned unexpected row count 
	 * from update [0]; actual row count: 0; expected: 1
	 * */
	@Override
	public void update(User entity) {
		getCurrentSession().update(entity);
	}

	/**
	 * 并不是简单的save/update结合体，这是根据配置主键unsaved-value的值决定的
	 * 该值默认为null，这种情况下如果主键字段是有值的，那么就一直执行update，如果找不到也会抛出update中找不到的那个异常
	 * 如果主键没有值，再执行save操作
	 * */
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
