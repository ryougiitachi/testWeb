package per.itachi.test.service.postgresql.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import per.itachi.test.dao.postgresql.UserDao;
import per.itachi.test.pojo.postgresql.User;
import per.itachi.test.service.postgresql.UserService;

@Service("defaultUserService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	@Qualifier("defaultUserDao")
	private UserDao userDao;

	@Override
	public User load(long id) {
		return userDao.load(id);
	}
	
	//No compiler error without invocation
//	public User load(Long id) {
//		return userDao.load(id);
//	}
	
	/**
	 * @param type	
	 * */
	@Override
	public User findUser(long id, int type) {
		User user = null;
		switch (type) {
		case 1:
			user = userDao.get(id);
			break;
		case 2:
			user = userDao.load(id);
			break;
		case 3:
			user = userDao.findUserByQBC(id);
			break;
		case 4:
			user = userDao.findUserByQBE(id);
			break;
		case 5:
			user = userDao.findUserByNamedQuery(id);
			break;
		default:
			user = userDao.findUserByNamedQuery(id);
			break;
		}
		return user;
	}

	@Override
	public User get(long id) {
		return userDao.get(id);
	}

	/**
	 * @param type	
	 * 			1 - findUsersByHQL()
	 * 			2 - findUsersByJPACriteria
	 * 			3 - findUsersByNativeQuery
	 * 			4 - findUsersByNamedQuery
	 * 			5 - findUsersByNamedNativeQuery
	 * */
	@Override
	public List<User> findAll(int type) {
		List<User> result = null;
		switch (type) {
		case 1:
			result = userDao.findUsersByHQL();
			break;
		case 2:
			result = userDao.findUsersByJPACriteria();
			break;
		case 3:
			result = userDao.findUsersByNativeQuery();
			break;
		case 4:
			result = userDao.findUsersByNamedQuery();
			break;
		case 5:
			result = userDao.findUsersByNamedNativeQuery();
			break;
		default:
			result = userDao.findAll();
			break;
		}
		return result;
	}
	
	@Override
	public List<User> findUsers(long id, int type) {
		return userDao.findUsersByNamedNativeQuery(id);
	}

	@Override
	public void persist(User entity) {
		userDao.persist(entity);
	}

	@Override
	public long save(User entity) {
		Timestamp insertDate = Timestamp.valueOf(LocalDateTime.now());
		entity.setInsertDate(insertDate);
		entity.setUpdateTime(LocalDateTime.now());
		return userDao.save(entity).longValue();
	}
	
	@Override
	public void update(User entity) {
		Timestamp insertDate = Timestamp.valueOf(LocalDateTime.now());
		entity.setInsertDate(insertDate);
		entity.setUpdateTime(LocalDateTime.now());
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
	
	/**
	 * 由于hibernate持久化特性，以下get/load之后出来的实体皆为持久化对象，事务传播模式配置为REQUIRED；
	 * 方法结束后事务即结束，在默认情况下，以下两个语句虽然没有显示执行save/update，方法结束之后hibernate会发送update语句进行更新；
	 * 如果在spring事务管理配置为read-only模式，只查询不更新，而且也不会报错。
	 * 
	 * http://www.cnblogs.com/xiaoluo501395377/p/3380270.html	hibernate实体各种状态之间转换的情况的许多实例
	 * */
	@Override
	public void testTrxPersistence() {
		User userGet1 = userDao.get(1l);
		User userLoad1 = userDao.load(2l);
		LocalDateTime now = LocalDateTime.now();
		userGet1.setLastAccessDatetime(now);//在非只读情况下发送update更新所有字段
		userLoad1.setLastAccessDatetime(now);//在非只读情况下发送update更新所有字段
//		userDao.update(userGet1);//只读情况下不会报错，但是不起作用，没有update语句，读写情况下，仅仅是一个持久化标志
	}
	
	/**
	 * 测试Hibernate缓存特性<br/>
	 * <br/>
	 * Hibernate持久化对象三状态(transient瞬时态，persistent持久态，detached离线态)指的是在缓存中的状态？<br/>
	 * <br/>
	 * http://www.cnblogs.com/wean/archive/2012/05/16/2502724.html 对比一级缓存与二级缓存的区别<br/>
	 * http://www.cnblogs.com/juaner767/p/5575076.html 哪些方法支持一级缓存<br/>
	 * 
	 * 支持一级缓存的方法get/load/save/iterate等，Query和Criteria的list()和uniqueResult()只会写缓存，但不会从缓存中读取（除非结合查询缓存）。
	 * 一级缓存无法取消，但可以管理，可以使用session.clear()、session.evict()清除或驱逐。（存疑）
	 * */
	@Override
	public void testSessionCache() {
		//这几个get/load出来的实体都是一个对象，如果load先执行就是代理对象，如果get先执行就是正常对象
		User userGet1 = userDao.get(3l);
		User userGet2 = userDao.get(3l);
		User userLoad1 = userDao.load(3l);
		//user1与user2两次结果查询处于同一事务中，所以只查询一次，两次为同一个对象
		logger.info("The user1 is the same as the user2? {}", userGet1==userGet2);//true
		logger.info("The hashcode of user1 is {}", userGet1.hashCode());
		logger.info("The hashcode of user2 is {}", userGet2.hashCode());
		//load方式也可以取出之前get的结果
		//即使让load先执行结果也是一样的，即两个get和一个load都是一个对象
		logger.info("The get is the same as the load? {}", userGet1==userLoad1);//true
		logger.info("The hashcode of get is {}", userGet1.hashCode());
		logger.info("The hashcode of load is {}", userLoad1.hashCode());
		User trx1Get1 = get(3l);
		//事务传播模式设置为REQUIRED，这个service方法也在同一事务中(?)，也是同一个对象
		logger.info("The user1 is the same as the trx1Get1? {}", userGet1==trx1Get1);//true
		logger.info("The hashcode of user1 is {}", userGet1.hashCode());
		logger.info("The hashcode of user2 is {}", trx1Get1.hashCode());
		
		List<User> listHQL1 = findAll(1);
		List<User> listHQL2 = findAll(1);
		List<User> listJPA1 = findAll(2);
		//通过list查询，两个list对象不是同一个，但是里面的内容都是一样的对象，而且会取之前get/load的缓存对象，每次list进行一次完整查询
		logger.info("The listHQL1 is the same as the listHQL2? {}", listHQL1==listHQL2);//false
		logger.info("The hashcode of listHQL1 is {}", System.identityHashCode(listHQL1));
		logger.info("The hashcode of listHQL2 is {}", System.identityHashCode(listHQL2));
		//getResultList也会发送SQL执行完整查询，Query.list与Query.getResultList的实现是一样的，getResultList调用list
		logger.info("The listHQL1 is the same as the listJPA1? {}", listHQL1==listJPA1);//false
		logger.info("The hashcode of listHQL1 is {}", System.identityHashCode(listHQL1));
		logger.info("The hashcode of listJPA1 is {}", System.identityHashCode(listJPA1));
		
		List<User> listIterator = new ArrayList<>();
		Iterator<User> iteratorUserHQL1 = userDao.findUsersByIterate();
//		Iterator<User> iteratorUserHQL2 = userDao.findUsersByIterate();//迭代对象不同，查出来的东西一样
		while (iteratorUserHQL1.hasNext()) {
			listIterator.add(iteratorUserHQL1.next());
		}
		//iterate会把数据库中符合条件的主键查出来，然后再逐个从数据库中查出，如果缓存中有复合的主键数据则直接用缓存的，
		//这样出来的实体类是代理对象，使用了延迟加载即在使用的时候才会查询，不影响size等大小数据的使用，
		//但在调试的时候如果点到某一个实体元素时可能会发送sql执行查询，应该是显示调用了实体对象方法触发了延迟加载
		logger.info("The content of user#1 is {}", listIterator.get(0));
		logger.info("The hashcode of iteratorUserHQL1 is {}", System.identityHashCode(iteratorUserHQL1));
	}
	
	/**
	 * hibernate默认二级缓存是ehcache，缓存级别分为事务/会话缓存、应用缓存、集群缓存；
	 * 二级缓存通常是指应用缓存，即只在单个jvm环境中起作用，可以起到在单个应用中减少数据库io提高查询效率的作用；
	 * ehcache在1.3之后开始支持集群缓存，现在用的更多的还是它的二级缓存。
	 * Query.get/load/iterate等方法支持二级缓存，Session.find总是到数据库中查询数据；
	 * 二级缓存比较适合存放多读少改，不太重要，比较少并发的数据，比如配置数据(?)
	 * 参考文章：http://developer.51cto.com/art/201202/315922.htm
	 * */
	@Override
	public void testSessionFactoryCache() {
		User userGet1 = userDao.get(1l);
		User userLoad1 = userDao.load(2l);
		//无论get/load每次请求查询出的对象都不是同一个，但是并没有发送sql语句查询数据库，二级缓存应该还是起作用了
		logger.info("The hashcode of user object via get is {}", System.identityHashCode(userGet1));
		logger.info("The hashcode of user object via load is {}", System.identityHashCode(userLoad1));
	}
}
